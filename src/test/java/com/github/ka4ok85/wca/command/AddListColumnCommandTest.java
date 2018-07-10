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
import com.github.ka4ok85.wca.constants.ListColumnType;
import com.github.ka4ok85.wca.options.AddListColumnOptions;
import com.github.ka4ok85.wca.response.AddListColumnResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class AddListColumnCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<AddListColumn>",
			"<LIST_ID>1</LIST_ID>", "<COLUMN_NAME>test column</COLUMN_NAME>", "<COLUMN_TYPE>0</COLUMN_TYPE>",
			"<DEFAULT></DEFAULT></AddListColumn>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		AddListColumnCommand command = new AddListColumnCommand();
		AddListColumnOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		AddListColumnCommand command = new AddListColumnCommand();
		AddListColumnOptions options = new AddListColumnOptions(1L, "test column", ListColumnType.TEXT, null);

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
	public void testBuildXmlHonorsDefaultValue() {
		// get XML from command
		AddListColumnCommand command = new AddListColumnCommand();
		AddListColumnOptions options = new AddListColumnOptions(1L, "test column", ListColumnType.TEXT, "test value");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<DEFAULT></DEFAULT>", "<DEFAULT>test value</DEFAULT>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlDoesNotHonorEmptyDefaultValue() {
		// get XML from command
		AddListColumnCommand command = new AddListColumnCommand();
		AddListColumnOptions options = new AddListColumnOptions(1L, "test column", ListColumnType.TEXT, "");

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
	public void testBuildXmlHonorsSelectionValues() {
		// get XML from command
		AddListColumnCommand command = new AddListColumnCommand();
		AddListColumnOptions options = new AddListColumnOptions(1L, "test column", ListColumnType.TEXT, null);
		List<String> selectionValues = new ArrayList<String>();
		selectionValues.add("selection Value 1");
		selectionValues.add("selection Value 2");
		options.setSelectionValues(selectionValues);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<DEFAULT></DEFAULT>",
				"<DEFAULT></DEFAULT><SELECTION_VALUES><VALUE>selection Value 1</VALUE><VALUE>selection Value 2</VALUE></SELECTION_VALUES>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		AddListColumnCommand command = context.getBean(AddListColumnCommand.class);
		AddListColumnOptions options = new AddListColumnOptions(1L, "test column", ListColumnType.TEXT, null);

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<AddListColumnResponse> responseContainer = command.readResponse(resultNode, options);
		AddListColumnResponse response = responseContainer.getResposne();

		assertEquals(response.getClass(), AddListColumnResponse.class);
	}

}
