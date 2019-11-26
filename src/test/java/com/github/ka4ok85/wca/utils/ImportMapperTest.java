package com.github.ka4ok85.wca.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.constants.ImportMapperAction;
import com.github.ka4ok85.wca.constants.ListColumnType;
import com.github.ka4ok85.wca.constants.ListType;
import com.github.ka4ok85.wca.constants.Visibility;

public class ImportMapperTest {
	private String defaultXml = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", 
			"<LIST_IMPORT>", 
			"<LIST_INFO>", 
				"<ACTION>CREATE</ACTION>",
				"<LIST_TYPE>0</LIST_TYPE>",
				"<LIST_VISIBILITY>1</LIST_VISIBILITY>",
				"<LIST_NAME>Test Import Mapper 3</LIST_NAME>",
				"<FILE_TYPE>0</FILE_TYPE>",
				"<HASHEADERS>true</HASHEADERS>",
			"</LIST_INFO>",
			
			"<SYNC_FIELDS>", 
			"<SYNC_FIELD>",
				"<NAME>Account</NAME>", 
			"</SYNC_FIELD>", 
			"</SYNC_FIELDS>", 
			
			"<COLUMNS>",
			"<COLUMN>", 
				"<NAME>", "<![CDATA[First Name]]>", "</NAME>", 
				"<TYPE>0</TYPE>",
				"<IS_REQUIRED>false</IS_REQUIRED>",
				"<KEY_COLUMN>false</KEY_COLUMN>",
			"</COLUMN>", 
			
			"<COLUMN>", 
				"<NAME>", "<![CDATA[Last Name]]>", "</NAME>", 
				"<TYPE>0</TYPE>",
				"<IS_REQUIRED>false</IS_REQUIRED>",
				"<KEY_COLUMN>false</KEY_COLUMN>",
				"<DEFAULT_VALUE><![CDATA[Doe]]></DEFAULT_VALUE>",
			"</COLUMN>", 
			
			"<COLUMN>", 
				"<NAME>", "<![CDATA[Account]]>", "</NAME>", 
				"<TYPE>2</TYPE>",
				"<IS_REQUIRED>false</IS_REQUIRED>",
				"<KEY_COLUMN>false</KEY_COLUMN>",
			"</COLUMN>",
			"</COLUMNS>",
			
			
			"<MAPPING>",
			"<COLUMN>",
				"<INDEX>1</INDEX>",
				"<NAME><![CDATA[First Name]]></NAME>",
				"<INCLUDE>true</INCLUDE>",
			"</COLUMN>",
			"<COLUMN>",
				"<INDEX>2</INDEX>",
				"<NAME><![CDATA[Last Name]]></NAME>",
				"<INCLUDE>false</INCLUDE>",
			"</COLUMN>",
			"<COLUMN>",
				"<INDEX>3</INDEX>",
				"<NAME><![CDATA[Account]]></NAME>",
				"<INCLUDE>true</INCLUDE>",
			"</COLUMN>",
			"</MAPPING>",
		
			"</LIST_IMPORT>");
	
	
	@Test
	public void testGenerateXMLString() {
		ImportMapper mapper = new ImportMapper(ImportMapperAction.CREATE, Visibility.SHARED);
		
		mapper.setListName("Test Import Mapper 3");
		
		List<String> syncFields = new ArrayList<String>();
		syncFields.add("Account");
		mapper.setSyncFields(syncFields );
		
		List<ImportMapperListColumn> columns = new ArrayList<ImportMapperListColumn>();
		
		ImportMapperListColumn column1 = new ImportMapperListColumn("First Name", ListColumnType.TEXT, false, false, true, null);
		ImportMapperListColumn column2 = new ImportMapperListColumn("Last Name", ListColumnType.TEXT, false, false, false, "Doe");
		ImportMapperListColumn column3 = new ImportMapperListColumn("Account", ListColumnType.NUMERIC, false, false, true, null);
		
		columns.add(column1);
		columns.add(column2);
		columns.add(column3);
		mapper.setColumns(columns);

		String testString = mapper.generateXMLString(); 
		Source test = Input.fromString(testString).build();
		
		String controlString = defaultXml;
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testGenerateXMLStringExistingList() {
		ImportMapper mapper = new ImportMapper(ImportMapperAction.UPDATE_ONLY, Visibility.SHARED);
		
		mapper.setListId(123L);
		
		List<String> syncFields = new ArrayList<String>();
		syncFields.add("Account");
		mapper.setSyncFields(syncFields );
		
		List<ImportMapperListColumn> columns = new ArrayList<ImportMapperListColumn>();
		
		ImportMapperListColumn column1 = new ImportMapperListColumn("First Name", ListColumnType.TEXT, false, false, true, null);
		ImportMapperListColumn column2 = new ImportMapperListColumn("Last Name", ListColumnType.TEXT, false, false, false, "Doe");
		ImportMapperListColumn column3 = new ImportMapperListColumn("Account", ListColumnType.NUMERIC, false, false, true, null);
		
		columns.add(column1);
		columns.add(column2);
		columns.add(column3);
		mapper.setColumns(columns);

		String testString = mapper.generateXMLString(); 
		Source test = Input.fromString(testString).build();
		
		String controlString = defaultXml.replace("<ACTION>CREATE</ACTION>", "<ACTION>UPDATE_ONLY</ACTION>").replace("<LIST_NAME>Test Import Mapper 3</LIST_NAME>", "<LIST_ID>123</LIST_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testSetListType() {
		ImportMapper mapper = new ImportMapper(ImportMapperAction.CREATE, Visibility.SHARED);
		
		mapper.setListName("Test Import Mapper 3");
		
		List<String> syncFields = new ArrayList<String>();
		syncFields.add("Account");
		mapper.setSyncFields(syncFields );
		
		List<ImportMapperListColumn> columns = new ArrayList<ImportMapperListColumn>();
		
		ImportMapperListColumn column1 = new ImportMapperListColumn("First Name", ListColumnType.TEXT, false, false, true, null);
		ImportMapperListColumn column2 = new ImportMapperListColumn("Last Name", ListColumnType.TEXT, false, false, false, "Doe");
		ImportMapperListColumn column3 = new ImportMapperListColumn("Account", ListColumnType.NUMERIC, false, false, true, null);
		
		columns.add(column1);
		columns.add(column2);
		columns.add(column3);
		mapper.setColumns(columns);
		
		mapper.setListType(ListType.SUPPRESSION_LISTS);

		String testString = mapper.generateXMLString(); 
		Source test = Input.fromString(testString).build();
		
		String controlString = defaultXml.replace("<LIST_TYPE>0</LIST_TYPE>", "<LIST_TYPE>" + ListType.SUPPRESSION_LISTS.value().toString() + "</LIST_TYPE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	
}
