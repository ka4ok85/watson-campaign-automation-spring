package com.github.ka4ok85.wca.utils;

import java.util.ArrayList;
import java.util.List;

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

}
