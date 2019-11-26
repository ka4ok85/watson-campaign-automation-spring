package com.github.ka4ok85.wca.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.constants.ImportFileFormat;
import com.github.ka4ok85.wca.constants.ImportMapperAction;
import com.github.ka4ok85.wca.constants.ListColumnType;
import com.github.ka4ok85.wca.constants.ListType;
import com.github.ka4ok85.wca.constants.Visibility;

public class ImportMapper {

	private final ImportMapperAction importMapperAction;
	private ListType listType = ListType.DATABASES;
	private String listName;
	private Long listId;
	private final Visibility visibility;
	private String parentFolderPath;
	private ImportFileFormat fileFormat = ImportFileFormat.CSV;
	private boolean hasHeaders = true;
	private boolean isEncodedAsMd5;
	private List<String> syncFields = new ArrayList<String>();
	private List<ImportMapperListColumn> columns = new ArrayList<ImportMapperListColumn>();
	private List<Long> contactLists = new ArrayList<Long>();

	private Document doc;
	private Node currentNode;

	public ImportMapper(ImportMapperAction importMapperAction, Visibility visibility) {
		super();
		this.importMapperAction = importMapperAction;
		this.visibility = visibility;
	}

	public void setListType(ListType listType) {
		if (listType != ListType.DATABASES && listType != ListType.SUPPRESSION_LISTS
				&& listType != ListType.SEED_LISTS) {
			throw new RuntimeException("Only Database, Suppression or Seed List supported");
		}

		this.listType = listType;
	}

	public void setListName(String listName) {
		if (listName == null || listName.trim().isEmpty()) {
			throw new RuntimeException("List Name must be non-empty String. Provided List Name = " + listName);
		}

		if (importMapperAction != ImportMapperAction.CREATE) {
			throw new RuntimeException("Import Mapper Action must be CREATE");
		}

		this.listName = listName;
		this.listId = null;
	}

	public void setListId(Long listId) {
		if (listId == null || listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		if (importMapperAction == ImportMapperAction.CREATE) {
			throw new RuntimeException("Import Mapper Action can not be CREATE");
		}

		this.listId = listId;
		this.listName = null;
		this.parentFolderPath = null;
	}

	public void setParentFolderPath(String parentFolderPath) {
		if (parentFolderPath == null || parentFolderPath.trim().isEmpty()) {
			throw new RuntimeException(
					"Parent Folder Path must be non-empty String. Provided Parent Folder Path = " + parentFolderPath);
		}

		if (importMapperAction != ImportMapperAction.CREATE) {
			throw new RuntimeException("Import Mapper Action must be CREATE");
		}

		this.parentFolderPath = parentFolderPath;
		this.listId = null;
	}

	public void setFileFormat(ImportFileFormat fileFormat) {
		if (fileFormat == null) {
			throw new RuntimeException("File Format can not be null");
		}

		this.fileFormat = fileFormat;
	}

	public void setHasHeaders(boolean hasHeaders) {
		this.hasHeaders = hasHeaders;
	}

	public void setEncodedAsMd5(boolean isEncodedAsMd5) {
		this.isEncodedAsMd5 = isEncodedAsMd5;
	}

	public void setSyncFields(List<String> syncFields) {
		if (syncFields == null) {
			throw new RuntimeException("Sync Fields can not be null");
		}

		this.syncFields = syncFields;
	}

	public void setColumns(List<ImportMapperListColumn> columns) {
		if (columns == null) {
			throw new RuntimeException("Columns can not be null");
		}

		this.columns = columns;
	}

	public void setContactLists(List<Long> contactLists) {
		if (contactLists == null) {
			throw new RuntimeException("Contact Lists can not be null");
		}

		if (importMapperAction == ImportMapperAction.CREATE || importMapperAction == ImportMapperAction.OPT_OUT) {
			throw new RuntimeException("Contact Lists can not be specified for Create and Opt Out actions");
		}

		this.contactLists = contactLists;
	}

	public void generateMapFile(String filePath) {
		String xml = generateXMLString();

		try {
			Files.write(Paths.get(filePath), xml.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public String generateXMLString() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			currentNode = doc;

			Element rootElement = doc.createElement("LIST_IMPORT");
			currentNode = addChildNode(rootElement, null);

			Element listInfoElement = doc.createElement("LIST_INFO");
			addChildNode(listInfoElement, null);

			Element actionElement = doc.createElement("ACTION");
			actionElement.setTextContent(importMapperAction.value());
			addChildNode(actionElement, listInfoElement);

			Element listTypeElement = doc.createElement("LIST_TYPE");
			listTypeElement.setTextContent(listType.value().toString());
			addChildNode(listTypeElement, listInfoElement);
			
			Element listVisibilityElement = doc.createElement("LIST_VISIBILITY");
			listVisibilityElement.setTextContent(visibility.value().toString());
			addChildNode(listVisibilityElement, listInfoElement);

			if (listName != null) {
				Element listNameElement = doc.createElement("LIST_NAME");
				listNameElement.setTextContent(listName);
				addChildNode(listNameElement, listInfoElement);
			}

			if (listId != null) {
				Element listIdElement = doc.createElement("LIST_ID");
				listIdElement.setTextContent(listId.toString());
				addChildNode(listIdElement, listInfoElement);
			}

			
			
			
			if (parentFolderPath != null) {
				Element parentFolderPathElement = doc.createElement("PARENT_FOLDER_PATH");
				parentFolderPathElement.setTextContent(parentFolderPath);
				addChildNode(parentFolderPathElement, listInfoElement);
			}

			Element fileFormatElement = doc.createElement("FILE_TYPE");
			fileFormatElement.setTextContent(fileFormat.value().toString());
			addChildNode(fileFormatElement, listInfoElement);

			if (hasHeaders) {
				Element hasHeadersElement = doc.createElement("HASHEADERS");
				hasHeadersElement.setTextContent("true");
				addChildNode(hasHeadersElement, listInfoElement);
			}

			if (isEncodedAsMd5) {
				Element isEncodedAsMd5Element = doc.createElement("ENCODED_AS_MD5");
				isEncodedAsMd5Element.setTextContent("true");
				addChildNode(isEncodedAsMd5Element, listInfoElement);
			}

			if (syncFields.size() > 0) {
				Element syncFieldsElement = doc.createElement("SYNC_FIELDS");
				addChildNode(syncFieldsElement, currentNode);
				for (String syncField : syncFields) {
					Element syncFieldElement = doc.createElement("SYNC_FIELD");
					addChildNode(syncFieldElement, syncFieldsElement);

					Element syncFieldNameElement = doc.createElement("NAME");
					syncFieldNameElement.setTextContent(syncField);
					addChildNode(syncFieldNameElement, syncFieldElement);
				}
			}

			if (columns.size() > 0) {
				Element columnsElement = doc.createElement("COLUMNS");
				addChildNode(columnsElement, currentNode);
				for (ImportMapperListColumn column : columns) {
					Element columnElement = doc.createElement("COLUMN");
					addChildNode(columnElement, columnsElement);

					Element columnNameElement = doc.createElement("NAME");
					CDATASection cdata = doc.createCDATASection(column.getName());
					columnNameElement.appendChild(cdata);
					addChildNode(columnNameElement, columnElement);

					Element columnTypeElement = doc.createElement("TYPE");
					columnTypeElement.setTextContent(column.getListColumnType().value().toString());
					addChildNode(columnTypeElement, columnElement);

					Element columnIsRequiredElement = doc.createElement("IS_REQUIRED");
					columnIsRequiredElement.setTextContent(Boolean.toString(column.isRequired()));
					addChildNode(columnIsRequiredElement, columnElement);

					Element columnIsKeyElement = doc.createElement("KEY_COLUMN");
					columnIsKeyElement.setTextContent(Boolean.toString(column.isKeyColumn()));
					addChildNode(columnIsKeyElement, columnElement);

					if (column.getDefaultValue() != null) {
						Element columnDefaultValueElement = doc.createElement("DEFAULT_VALUE");
						cdata = doc.createCDATASection(column.getDefaultValue());
						columnDefaultValueElement.appendChild(cdata);
						addChildNode(columnDefaultValueElement, columnElement);
					}

					if (column.getListColumnType().equals(ListColumnType.SELECTION)) {
						Element selectionValuesElement = doc.createElement("SELECTION_VALUES");
						addChildNode(selectionValuesElement, columnElement);
						for (String selection : column.getSelectionValues()) {
							Element selectionValueElement = doc.createElement("VALUE");
							cdata = doc.createCDATASection(selection);
							selectionValueElement.appendChild(cdata);
							addChildNode(selectionValueElement, selectionValuesElement);
						}
					}

				}
			}

			if (columns.size() > 0) {
				Element mappingElement = doc.createElement("MAPPING");
				addChildNode(mappingElement, currentNode);
				int index = 0;
				for (ImportMapperListColumn column : columns) {
					index++;

					Element columnElement = doc.createElement("COLUMN");
					addChildNode(columnElement, mappingElement);

					Element columnIndexElement = doc.createElement("INDEX");
					columnIndexElement.setTextContent(String.valueOf(index));
					addChildNode(columnIndexElement, columnElement);

					Element columnNameElement = doc.createElement("NAME");
					CDATASection cdata = doc.createCDATASection(column.getName());
					columnNameElement.appendChild(cdata);
					addChildNode(columnNameElement, columnElement);

					Element columnIncludeElement = doc.createElement("INCLUDE");
					columnIncludeElement.setTextContent(Boolean.toString(column.isIncluded()));
					addChildNode(columnIncludeElement, columnElement);
				}
			}

			if (contactLists.size() > 0) {
				Element contactListsElement = doc.createElement("CONTACT_LISTS");
				addChildNode(contactListsElement, currentNode);
				for (Long contactList : contactLists) {
					Element contactListElement = doc.createElement("CONTACT_LIST_ID");
					contactListElement.setTextContent(contactList.toString());
					addChildNode(contactListElement, contactListsElement);
				}
			}

			return getXML();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Node addChildNode(Node childNode, Node parentNode) {
		if (parentNode == null) {
			this.currentNode.appendChild(childNode);
		} else {
			parentNode.appendChild(childNode);
		}

		return childNode;
	}

	public String getXML() {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writer.toString();
	}

	@Override
	public String toString() {
		return "ImportMapper [importMapperAction=" + importMapperAction + ", listType=" + listType + ", listName="
				+ listName + ", listId=" + listId + ", visibility=" + visibility + ", parentFolderPath="
				+ parentFolderPath + ", fileFormat=" + fileFormat + ", hasHeaders=" + hasHeaders + ", isEncodedAsMd5="
				+ isEncodedAsMd5 + ", syncFields=" + syncFields + ", columns=" + columns + ", contactLists="
				+ contactLists + "]";
	}

}
