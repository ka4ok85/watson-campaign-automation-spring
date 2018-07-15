package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class InsertUpdateRelationalTableCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<InsertUpdateRelationalTable>", "<TABLE_ID>1</TABLE_ID>", "<ROWS>", "<ROW>",
			"<COLUMN name=\"CUSTOMER_ID\">", "<![CDATA[4]]>", "</COLUMN>", "<COLUMN name=\"RT_Identifier\">",
			"<![CDATA[5]]>", "</COLUMN>", "</ROW>", "</ROWS>", "</InsertUpdateRelationalTable>", "</Body>",
			"</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		InsertUpdateRelationalTableCommand command = new InsertUpdateRelationalTableCommand();
		InsertUpdateRelationalTableOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		InsertUpdateRelationalTableCommand command = new InsertUpdateRelationalTableCommand();
		InsertUpdateRelationalTableOptions options = new InsertUpdateRelationalTableOptions(1L);

		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		Map<String, String> row1 = new HashMap<String, String>();
		row1.put("CUSTOMER_ID", "4");
		row1.put("RT_Identifier", "5");
		rows.add(row1);

		options.setRows(rows);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest;
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
}
