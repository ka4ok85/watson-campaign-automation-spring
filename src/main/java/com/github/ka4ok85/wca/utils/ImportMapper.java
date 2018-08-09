package com.github.ka4ok85.wca.utils;

import java.io.StringWriter;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.constants.ImportFileFormat;
import com.github.ka4ok85.wca.constants.ImportMapperAction;
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

	public ImportMapperAction getImportMapperAction() {
		return importMapperAction;
	}

	public ListType getListType() {
		return listType;
	}

	public void setListType(ListType listType) {
		if (listType != ListType.DATABASES || listType != ListType.SUPPRESSION_LISTS
				|| listType != ListType.SEED_LISTS) {
			throw new RuntimeException("Only Database, Suppression or Seed List supported");
		}

		this.listType = listType;
	}

	public String getListName() {
		return listName;
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

	public Long getListId() {
		return listId;
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
	}

	public String getParentFolderPath() {
		return parentFolderPath;
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

	public Visibility getVisibility() {
		return visibility;
	}

	public ImportFileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(ImportFileFormat fileFormat) {
		if (fileFormat == null) {
			throw new RuntimeException("File Format can not be null");
		}

		this.fileFormat = fileFormat;
	}

	public boolean isHasHeaders() {
		return hasHeaders;
	}

	public void setHasHeaders(boolean hasHeaders) {
		this.hasHeaders = hasHeaders;
	}

	public boolean isEncodedAsMd5() {
		return isEncodedAsMd5;
	}

	public void setEncodedAsMd5(boolean isEncodedAsMd5) {
		this.isEncodedAsMd5 = isEncodedAsMd5;
	}

	public List<String> getSyncFields() {
		return syncFields;
	}

	public void setSyncFields(List<String> syncFields) {
		if (syncFields == null) {
			throw new RuntimeException("Sync Fields can not be null");
		}

		this.syncFields = syncFields;
	}

	public List<ImportMapperListColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<ImportMapperListColumn> columns) {
		this.columns = columns;
	}

	public List<Long> getContactLists() {
		return contactLists;
	}

	public void setContactLists(List<Long> contactLists) {
		this.contactLists = contactLists;
	}

	
	
	
	public void generateFile(String fileName) {
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
			
			
			String out = getXML();
			System.out.println(out);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
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
