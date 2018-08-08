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

	protected ImportMapperAction getImportMapperAction() {
		return importMapperAction;
	}

	protected ListType getListType() {
		return listType;
	}

	protected void setListType(ListType listType) {
		if (listType != ListType.DATABASES || listType != ListType.SUPPRESSION_LISTS
				|| listType != ListType.SEED_LISTS) {
			throw new RuntimeException("Only Database, Suppression or Seed List supported");
		}

		this.listType = listType;
	}

	protected String getListName() {
		return listName;
	}

	protected void setListName(String listName) {
		if (listName == null || listName.trim().isEmpty()) {
			throw new RuntimeException("List Name must be non-empty String. Provided List Name = " + listName);
		}

		if (importMapperAction != ImportMapperAction.CREATE) {
			throw new RuntimeException("Import Mapper Action must be CREATE");
		}

		this.listName = listName;
		this.listId = null;
	}

	protected Long getListId() {
		return listId;
	}

	protected void setListId(Long listId) {
		if (listId == null || listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		if (importMapperAction == ImportMapperAction.CREATE) {
			throw new RuntimeException("Import Mapper Action can not be CREATE");
		}

		this.listId = listId;
		this.listName = null;
	}

	protected String getParentFolderPath() {
		return parentFolderPath;
	}

	protected void setParentFolderPath(String parentFolderPath) {
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

	protected Visibility getVisibility() {
		return visibility;
	}

	protected ImportFileFormat getFileFormat() {
		return fileFormat;
	}

	protected void setFileFormat(ImportFileFormat fileFormat) {
		if (fileFormat == null) {
			throw new RuntimeException("File Format can not be null");
		}

		this.fileFormat = fileFormat;
	}

	protected boolean isHasHeaders() {
		return hasHeaders;
	}

	protected void setHasHeaders(boolean hasHeaders) {
		this.hasHeaders = hasHeaders;
	}

	protected boolean isEncodedAsMd5() {
		return isEncodedAsMd5;
	}

	protected void setEncodedAsMd5(boolean isEncodedAsMd5) {
		this.isEncodedAsMd5 = isEncodedAsMd5;
	}

	protected List<String> getSyncFields() {
		return syncFields;
	}

	protected void setSyncFields(List<String> syncFields) {
		this.syncFields = syncFields;
	}

	protected List<ImportMapperListColumn> getColumns() {
		return columns;
	}

	protected void setColumns(List<ImportMapperListColumn> columns) {
		this.columns = columns;
	}

	protected List<Long> getContactLists() {
		return contactLists;
	}

	protected void setContactLists(List<Long> contactLists) {
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
			
			
			String out = getXML();
			System.out.println(out);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
	}

	protected Node addChildNode(Node childNode, Node parentNode) {
		if (parentNode == null) {
			this.currentNode.appendChild(childNode);
		} else {
			parentNode.appendChild(childNode);
		}

		return childNode;
	}
	
	protected String getXML() {
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
