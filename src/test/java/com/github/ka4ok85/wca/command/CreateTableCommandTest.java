package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.RelationalTableColumnType;
import com.github.ka4ok85.wca.options.CreateTableOptions;
import com.github.ka4ok85.wca.options.containers.RelationalTableColumn;
import com.github.ka4ok85.wca.response.CreateTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class CreateTableCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<CreateTable>",
			"<TABLE_NAME>test RT</TABLE_NAME>", "<COLUMNS>", "<COLUMN>", "<NAME>", "<![CDATA[column1]]>", "</NAME>",
			"<TYPE>", "<![CDATA[TEXT]]>", "</TYPE>", "<IS_REQUIRED>true</IS_REQUIRED>", "<KEY_COLUMN>true</KEY_COLUMN>",
			"<DEFAULT_VALUE>test default value</DEFAULT_VALUE>", "</COLUMN>", "<COLUMN>", "<NAME>",
			"<![CDATA[column2]]>", "</NAME>", "<TYPE>", "<![CDATA[SELECTION]]>", "</TYPE>", "<SELECTION_VALUES>",
			"<VALUE>", "<![CDATA[selection value 1]]>", "</VALUE>", "<VALUE>", "<![CDATA[selection value 2]]>",
			"</VALUE>", "</SELECTION_VALUES>", "</COLUMN>", "</COLUMNS>", "</CreateTable>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		CreateTableCommand command = new CreateTableCommand();
		CreateTableOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		CreateTableCommand command = new CreateTableCommand();
		CreateTableOptions options = new CreateTableOptions("test RT");

		List<RelationalTableColumn> columns = new ArrayList<RelationalTableColumn>();
		RelationalTableColumn column = new RelationalTableColumn("column1", RelationalTableColumnType.TEXT);
		column.setIsKeyColumn(true);
		column.setIsRequired(true);
		column.setDefaultValue("test default value");
		columns.add(column);
		RelationalTableColumn column2 = new RelationalTableColumn("column2", RelationalTableColumnType.SELECTION);
		List<String> selectionValues = new ArrayList<String>();
		selectionValues.add("selection value 1");
		selectionValues.add("selection value 2");
		column2.setSelectionValues(selectionValues);
		columns.add(column2);
		options.setColumns(columns);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest;
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsEmptyColumns() {
		// get XML from command
		CreateTableCommand command = new CreateTableCommand();
		CreateTableOptions options = new CreateTableOptions("test RT");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlStringPart = String.join(System.getProperty("line.separator"), "<TABLE_NAME>test RT</TABLE_NAME>",
				"<COLUMNS>", "<COLUMN>", "<NAME>", "<![CDATA[column1]]>", "</NAME>", "<TYPE>", "<![CDATA[TEXT]]>",
				"</TYPE>", "<IS_REQUIRED>true</IS_REQUIRED>", "<KEY_COLUMN>true</KEY_COLUMN>",
				"<DEFAULT_VALUE>test default value</DEFAULT_VALUE>", "</COLUMN>", "<COLUMN>", "<NAME>",
				"<![CDATA[column2]]>", "</NAME>", "<TYPE>", "<![CDATA[SELECTION]]>", "</TYPE>", "<SELECTION_VALUES>",
				"<VALUE>", "<![CDATA[selection value 1]]>", "</VALUE>", "<VALUE>", "<![CDATA[selection value 2]]>",
				"</VALUE>", "</SELECTION_VALUES>", "</COLUMN>", "</COLUMNS>");

		String controlString = defaultRequest.replace(controlStringPart, "<TABLE_NAME>test RT</TABLE_NAME>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		CreateTableCommand command = context.getBean(CreateTableCommand.class);
		CreateTableOptions options = new CreateTableOptions("test RT");

		Long tableId = 123L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><TABLE_ID>" + tableId + "</TABLE_ID></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<CreateTableResponse> responseContainer = command.readResponse(resultNode, options);
		CreateTableResponse response = responseContainer.getResposne();

		assertEquals(response.getTableId(), tableId);
	}
}
