package com.github.ka4ok85.wca.utils;

import com.github.ka4ok85.wca.constants.ImportMapperAction;
import com.github.ka4ok85.wca.constants.ListType;

public class ImportMapper {

	private final ImportMapperAction importMapperAction;
	private ListType listType = ListType.DATABASES;
	private String listName;

	public ImportMapper(ImportMapperAction importMapperAction) {
		super();
		this.importMapperAction = importMapperAction;
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
	}

}
