package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.github.ka4ok85.wca.options.DeleteRelationalTableDataOptions;
import com.github.ka4ok85.wca.response.DeleteRelationalTableDataResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class DeleteRelationalTableDataCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<DeleteRelationalTableData>", "<TABLE_ID>1</TABLE_ID>", "<ROWS>", "<ROW>",
			"<KEY_COLUMN name=\"CUSTOMER_ID\">1</KEY_COLUMN>", "<KEY_COLUMN name=\"RT_Identifier\">2</KEY_COLUMN>",
			"</ROW>", "</ROWS>", "</DeleteRelationalTableData>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		DeleteRelationalTableDataCommand command = new DeleteRelationalTableDataCommand();
		DeleteRelationalTableDataOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		DeleteRelationalTableDataCommand command = new DeleteRelationalTableDataCommand();
		DeleteRelationalTableDataOptions options = new DeleteRelationalTableDataOptions(1L);

		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		Map<String, String> row1 = new HashMap<String, String>();
		row1.put("CUSTOMER_ID", "1");
		row1.put("RT_Identifier", "2");
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

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresRows() {
		// get XML from command
		DeleteRelationalTableDataCommand command = new DeleteRelationalTableDataCommand();
		DeleteRelationalTableDataOptions options = new DeleteRelationalTableDataOptions(1L);

		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		Map<String, String> row1 = new HashMap<String, String>();
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

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresSomeRows() {
		// get XML from command
		DeleteRelationalTableDataCommand command = new DeleteRelationalTableDataCommand();
		DeleteRelationalTableDataOptions options = new DeleteRelationalTableDataOptions(1L);

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
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		DeleteRelationalTableDataCommand command = context.getBean(DeleteRelationalTableDataCommand.class);
		DeleteRelationalTableDataOptions options = new DeleteRelationalTableDataOptions(1L);
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		Map<String, String> row1 = new HashMap<String, String>();
		row1.put("a", "n");
		row1.put("recipid", "1");
		rows.add(row1);
		options.setRows(rows);

		String column1 = "a";
		String value1 = "n";
		String column2 = "recipid";
		String value2 = "1";
		String envelope = "<RESULT><SUCCESS>true</SUCCESS><FAILURES><FAILURE failure_type=\"permanent\" description=\"There is no column "
				+ column1 + "\" ><COLUMN name=\"" + column1 + "\"><![CDATA[" + value1 + "]]></COLUMN><COLUMN name=\""
				+ column2 + "\"><![CDATA[" + value2
				+ "]]></COLUMN></FAILURE><FAILURE failure_type=\"permanent\" description=\"There is no column "
				+ column2 + "\"><COLUMN name=\"" + column1 + "\"><![CDATA[" + value1 + "]]></COLUMN><COLUMN name=\""
				+ column2 + "\"><![CDATA[" + value2 + "]]></COLUMN></FAILURE></FAILURES></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<DeleteRelationalTableDataResponse> responseContainer = command.readResponse(resultNode,
				options);
		DeleteRelationalTableDataResponse response = responseContainer.getResposne();

		assertNotNull(response.getFailures());
		assertEquals(response.getFailures().size(), 2);
		assertEquals(response.getFailures().get(0).getFailureType(), "permanent");
		assertEquals(response.getFailures().get(0).getDescription(), "There is no column " + column1);
		assertNotNull(response.getFailures().get(0).getColumns());
		assertEquals(response.getFailures().get(0).getColumns().size(), 2);
		assertTrue(response.getFailures().get(0).getColumns().get(0).containsKey(column1));
		assertEquals(response.getFailures().get(0).getColumns().get(0).get(column1), value1);

		assertEquals(response.getFailures().get(1).getFailureType(), "permanent");
		assertEquals(response.getFailures().get(1).getDescription(), "There is no column " + column2);
		assertNotNull(response.getFailures().get(1).getColumns());
		assertEquals(response.getFailures().get(1).getColumns().size(), 2);
		assertTrue(response.getFailures().get(1).getColumns().get(1).containsKey(column2));
		assertEquals(response.getFailures().get(1).getColumns().get(1).get(column2), value2);
	}

}
