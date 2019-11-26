package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.github.ka4ok85.wca.options.GetListMetaDataOptions;
import com.github.ka4ok85.wca.response.GetListMetaDataResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetListMetaDataCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<GetListMetaData>",
			"<LIST_ID>1</LIST_ID>", "</GetListMetaData>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		GetListMetaDataCommand command = new GetListMetaDataCommand();
		GetListMetaDataOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		GetListMetaDataCommand command = new GetListMetaDataCommand();
		GetListMetaDataOptions options = new GetListMetaDataOptions(1L);

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
		GetListMetaDataCommand command = context.getBean(GetListMetaDataCommand.class);
		GetListMetaDataOptions options = new GetListMetaDataOptions(1L);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		Long id = 1L;
		String name = "test mailing";
		Long numOptOuts = 2L;
		Long numUndeliverable = 3L;
		Long parentDatabaseId = 4L;
		Long size = 5L;
		String created = "08/23/17 08:54 AM";
		String lastConfigured = "08/30/17 06:33 PM";
		String lastModified = "08/12/18 11:25 PM";
		String organizationId = "111-222-333";
		Integer type = 1;
		String userId = "333-444-555";
		Integer visibility = 1;
		boolean isOptInAutoreplyDefined = true;
		boolean isOptInFormDefined = true;
		boolean isOptOutFormDefined = true;
		boolean isProfileAutoreplyDefined = true;
		boolean isProfileFormDefined = true;

		String keyColumn = "RECIPIENT_ID";
		String column1 = "LIST_ID";
		String column2 = "MAILING_ID";
		String column2_value1 = "test value 1";
		String column2_value2 = "test value 2";

		String column3 = "COLUMN 3";
		String column3_default = "COLUMN 3 default value";
		Integer column3_type = ListColumnType.TIME.value();

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><ID>" + id + "</ID><NAME>" + name + "</NAME><TYPE>" + type
				+ "</TYPE><SIZE>" + size + "</SIZE><NUM_OPT_OUTS>" + numOptOuts + "</NUM_OPT_OUTS><NUM_UNDELIVERABLE>"
				+ numUndeliverable + "</NUM_UNDELIVERABLE><LAST_MODIFIED>" + lastModified
				+ "</LAST_MODIFIED><LAST_CONFIGURED>" + lastConfigured + "</LAST_CONFIGURED><CREATED>" + created
				+ "</CREATED><VISIBILITY>" + visibility + "</VISIBILITY><USER_ID>" + userId
				+ "</USER_ID><ORGANIZATION_ID>" + organizationId + "</ORGANIZATION_ID><PARENT_DATABASE_ID>"
				+ parentDatabaseId + "</PARENT_DATABASE_ID><OPT_IN_FORM_DEFINED>" + isOptInFormDefined
				+ "</OPT_IN_FORM_DEFINED><OPT_OUT_FORM_DEFINED>" + isOptOutFormDefined
				+ "</OPT_OUT_FORM_DEFINED><PROFILE_FORM_DEFINED>" + isProfileFormDefined
				+ "</PROFILE_FORM_DEFINED><OPT_IN_AUTOREPLY_DEFINED>" + isOptInAutoreplyDefined
				+ "</OPT_IN_AUTOREPLY_DEFINED><PROFILE_AUTOREPLY_DEFINED>" + isProfileAutoreplyDefined
				+ "</PROFILE_AUTOREPLY_DEFINED><COLUMNS><COLUMN><NAME>" + column1 + "</NAME></COLUMN><COLUMN><NAME>"
				+ column2 + "</NAME><SELECTION_VALUES><VALUE>" + column2_value1 + "</VALUE><VALUE>" + column2_value2
				+ "</VALUE></SELECTION_VALUES></COLUMN><COLUMN><NAME>" + column3 + "</NAME><DEFAULT_VALUE>"
				+ column3_default + "</DEFAULT_VALUE><TYPE>" + column3_type
				+ "</TYPE></COLUMN></COLUMNS><KEY_COLUMNS><COLUMN><NAME>" + keyColumn
				+ "</NAME></COLUMN></KEY_COLUMNS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetListMetaDataResponse> responseContainer = command.readResponse(resultNode, options);
		GetListMetaDataResponse response = responseContainer.getResposne();

		assertEquals(response.getId(), id);
		assertEquals(response.getName(), name);
		assertEquals(response.getNumOptOuts(), numOptOuts);
		assertEquals(response.getNumUndeliverable(), numUndeliverable);
		assertEquals(response.getParentDatabaseId(), parentDatabaseId);
		assertEquals(response.getSize(), size);
		assertEquals(response.getCreated(), LocalDateTime.parse(created, formatter));
		assertEquals(response.getLastConfigured(), LocalDateTime.parse(lastConfigured, formatter));
		assertEquals(response.getLastModified(), LocalDateTime.parse(lastModified, formatter));
		assertEquals(response.getOrganizationId(), organizationId);
		assertEquals(response.getType(), type);
		assertEquals(response.getUserId(), userId);
		assertEquals(response.getVisibility().value(), visibility);
		assertEquals(response.isOptInAutoreplyDefined(), isOptInAutoreplyDefined);
		assertEquals(response.isOptInFormDefined(), isOptInFormDefined);
		assertEquals(response.isOptOutFormDefined(), isOptOutFormDefined);
		assertEquals(response.isProfileAutoreplyDefined(), isProfileAutoreplyDefined);
		assertEquals(response.isProfileFormDefined(), isProfileFormDefined);

		assertEquals(response.getKeyColumns().size(), 1);
		assertEquals(response.getKeyColumns().get(0), keyColumn);

		assertEquals(response.getColumns().size(), 3);
		assertEquals(response.getColumns().get(0).getName(), column1);
		assertEquals(response.getColumns().get(1).getName(), column2);
		assertEquals(response.getColumns().get(1).getSelectionValues().size(), 2);
		assertEquals(response.getColumns().get(1).getSelectionValues().get(0), column2_value1);
		assertEquals(response.getColumns().get(1).getSelectionValues().get(1), column2_value2);
		assertEquals(response.getColumns().get(2).getName(), column3);
		assertEquals(response.getColumns().get(2).getDefaultValue(), column3_default);
		assertEquals(response.getColumns().get(2).getType().value(), column3_type);
	}

	@Test
	public void testReadResponseHonorsDateFormat()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetListMetaDataCommand command = context.getBean(GetListMetaDataCommand.class);
		GetListMetaDataOptions options = new GetListMetaDataOptions(1L);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		Long id = 1L;
		String name = "test mailing";
		Long numOptOuts = 2L;
		Long numUndeliverable = 3L;
		Long parentDatabaseId = 4L;
		Long size = 5L;
		String created = "08/23/17 12:54 AM";
		String lastConfigured = "08/30/17 12:33 PM";
		String lastModified = "08/12/18 12:25 PM";
		String organizationId = "111-222-333";
		Integer type = 1;
		String userId = "333-444-555";
		Integer visibility = 1;
		boolean isOptInAutoreplyDefined = true;
		boolean isOptInFormDefined = true;
		boolean isOptOutFormDefined = true;
		boolean isProfileAutoreplyDefined = true;
		boolean isProfileFormDefined = true;

		String keyColumn = "RECIPIENT_ID";
		String column1 = "LIST_ID";
		String column2 = "MAILING_ID";
		String column2_value1 = "test value 1";
		String column2_value2 = "test value 2";

		String column3 = "COLUMN 3";
		String column3_default = "COLUMN 3 default value";
		Integer column3_type = ListColumnType.TIME.value();

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><ID>" + id + "</ID><NAME>" + name + "</NAME><TYPE>" + type
				+ "</TYPE><SIZE>" + size + "</SIZE><NUM_OPT_OUTS>" + numOptOuts + "</NUM_OPT_OUTS><NUM_UNDELIVERABLE>"
				+ numUndeliverable + "</NUM_UNDELIVERABLE><LAST_MODIFIED>" + lastModified
				+ "</LAST_MODIFIED><LAST_CONFIGURED>" + lastConfigured + "</LAST_CONFIGURED><CREATED>" + created
				+ "</CREATED><VISIBILITY>" + visibility + "</VISIBILITY><USER_ID>" + userId
				+ "</USER_ID><ORGANIZATION_ID>" + organizationId + "</ORGANIZATION_ID><PARENT_DATABASE_ID>"
				+ parentDatabaseId + "</PARENT_DATABASE_ID><OPT_IN_FORM_DEFINED>" + isOptInFormDefined
				+ "</OPT_IN_FORM_DEFINED><OPT_OUT_FORM_DEFINED>" + isOptOutFormDefined
				+ "</OPT_OUT_FORM_DEFINED><PROFILE_FORM_DEFINED>" + isProfileFormDefined
				+ "</PROFILE_FORM_DEFINED><OPT_IN_AUTOREPLY_DEFINED>" + isOptInAutoreplyDefined
				+ "</OPT_IN_AUTOREPLY_DEFINED><PROFILE_AUTOREPLY_DEFINED>" + isProfileAutoreplyDefined
				+ "</PROFILE_AUTOREPLY_DEFINED><COLUMNS><COLUMN><NAME>" + column1 + "</NAME></COLUMN><COLUMN><NAME>"
				+ column2 + "</NAME><SELECTION_VALUES><VALUE>" + column2_value1 + "</VALUE><VALUE>" + column2_value2
				+ "</VALUE></SELECTION_VALUES></COLUMN><COLUMN><NAME>" + column3 + "</NAME><DEFAULT_VALUE>"
				+ column3_default + "</DEFAULT_VALUE><TYPE>" + column3_type
				+ "</TYPE></COLUMN></COLUMNS><KEY_COLUMNS><COLUMN><NAME>" + keyColumn
				+ "</NAME></COLUMN></KEY_COLUMNS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetListMetaDataResponse> responseContainer = command.readResponse(resultNode, options);
		GetListMetaDataResponse response = responseContainer.getResposne();

		assertEquals(response.getId(), id);
		assertEquals(response.getName(), name);
		assertEquals(response.getNumOptOuts(), numOptOuts);
		assertEquals(response.getNumUndeliverable(), numUndeliverable);
		assertEquals(response.getParentDatabaseId(), parentDatabaseId);
		assertEquals(response.getSize(), size);
		assertEquals(response.getCreated(), LocalDateTime.parse(created, formatter));
		assertEquals(response.getLastConfigured(), LocalDateTime.parse(lastConfigured, formatter));
		assertEquals(response.getLastModified(), LocalDateTime.parse(lastModified, formatter));
		assertEquals(response.getOrganizationId(), organizationId);
		assertEquals(response.getType(), type);
		assertEquals(response.getUserId(), userId);
		assertEquals(response.getVisibility().value(), visibility);
		assertEquals(response.isOptInAutoreplyDefined(), isOptInAutoreplyDefined);
		assertEquals(response.isOptInFormDefined(), isOptInFormDefined);
		assertEquals(response.isOptOutFormDefined(), isOptOutFormDefined);
		assertEquals(response.isProfileAutoreplyDefined(), isProfileAutoreplyDefined);
		assertEquals(response.isProfileFormDefined(), isProfileFormDefined);

		assertEquals(response.getKeyColumns().size(), 1);
		assertEquals(response.getKeyColumns().get(0), keyColumn);

		assertEquals(response.getColumns().size(), 3);
		assertEquals(response.getColumns().get(0).getName(), column1);
		assertEquals(response.getColumns().get(1).getName(), column2);
		assertEquals(response.getColumns().get(1).getSelectionValues().size(), 2);
		assertEquals(response.getColumns().get(1).getSelectionValues().get(0), column2_value1);
		assertEquals(response.getColumns().get(1).getSelectionValues().get(1), column2_value2);
		assertEquals(response.getColumns().get(2).getName(), column3);
		assertEquals(response.getColumns().get(2).getDefaultValue(), column3_default);
		assertEquals(response.getColumns().get(2).getType().value(), column3_type);
	}
	
	@Test
	public void testReadResponseHonorsEmptyNodes()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetListMetaDataCommand command = context.getBean(GetListMetaDataCommand.class);
		GetListMetaDataOptions options = new GetListMetaDataOptions(1L);

		Long id = 1L;
		String name = "test mailing";
		Long numOptOuts = 2L;
		Long numUndeliverable = 3L;
		Long size = 5L;
		String organizationId = "111-222-333";
		Integer type = 1;
		String userId = "333-444-555";
		Integer visibility = 1;
		boolean isOptInAutoreplyDefined = true;
		boolean isOptInFormDefined = true;
		boolean isOptOutFormDefined = true;
		boolean isProfileAutoreplyDefined = true;
		boolean isProfileFormDefined = true;

		String keyColumn = "RECIPIENT_ID";
		String column1 = "LIST_ID";
		String column2 = "MAILING_ID";
		String column2_value1 = "test value 1";
		String column2_value2 = "test value 2";

		String column3 = "COLUMN 3";
		String column3_default = "COLUMN 3 default value";
		Integer column3_type = ListColumnType.TIME.value();

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><ID>" + id + "</ID><NAME>" + name + "</NAME><TYPE>" + type
				+ "</TYPE><SIZE>" + size + "</SIZE><NUM_OPT_OUTS>" + numOptOuts + "</NUM_OPT_OUTS><NUM_UNDELIVERABLE>"
				+ numUndeliverable
				+ "</NUM_UNDELIVERABLE><LAST_MODIFIED></LAST_MODIFIED><LAST_CONFIGURED></LAST_CONFIGURED><CREATED></CREATED><VISIBILITY>"
				+ visibility + "</VISIBILITY><USER_ID>" + userId + "</USER_ID><ORGANIZATION_ID>" + organizationId
				+ "</ORGANIZATION_ID><PARENT_DATABASE_ID></PARENT_DATABASE_ID><OPT_IN_FORM_DEFINED>"
				+ isOptInFormDefined + "</OPT_IN_FORM_DEFINED><OPT_OUT_FORM_DEFINED>" + isOptOutFormDefined
				+ "</OPT_OUT_FORM_DEFINED><PROFILE_FORM_DEFINED>" + isProfileFormDefined
				+ "</PROFILE_FORM_DEFINED><OPT_IN_AUTOREPLY_DEFINED>" + isOptInAutoreplyDefined
				+ "</OPT_IN_AUTOREPLY_DEFINED><PROFILE_AUTOREPLY_DEFINED>" + isProfileAutoreplyDefined
				+ "</PROFILE_AUTOREPLY_DEFINED><COLUMNS><COLUMN><NAME>" + column1 + "</NAME></COLUMN><COLUMN><NAME>"
				+ column2 + "</NAME><SELECTION_VALUES><VALUE>" + column2_value1 + "</VALUE><VALUE>" + column2_value2
				+ "</VALUE></SELECTION_VALUES></COLUMN><COLUMN><NAME>" + column3 + "</NAME><DEFAULT_VALUE>"
				+ column3_default + "</DEFAULT_VALUE><TYPE>" + column3_type
				+ "</TYPE></COLUMN></COLUMNS><KEY_COLUMNS><COLUMN><NAME>" + keyColumn
				+ "</NAME></COLUMN></KEY_COLUMNS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetListMetaDataResponse> responseContainer = command.readResponse(resultNode, options);
		GetListMetaDataResponse response = responseContainer.getResposne();

		assertEquals(response.getId(), id);
		assertEquals(response.getName(), name);
		assertEquals(response.getNumOptOuts(), numOptOuts);
		assertEquals(response.getNumUndeliverable(), numUndeliverable);
		assertEquals(response.getSize(), size);
		assertEquals(response.getOrganizationId(), organizationId);
		assertEquals(response.getType(), type);
		assertEquals(response.getUserId(), userId);
		assertEquals(response.getVisibility().value(), visibility);
		assertEquals(response.isOptInAutoreplyDefined(), isOptInAutoreplyDefined);
		assertEquals(response.isOptInFormDefined(), isOptInFormDefined);
		assertEquals(response.isOptOutFormDefined(), isOptOutFormDefined);
		assertEquals(response.isProfileAutoreplyDefined(), isProfileAutoreplyDefined);
		assertEquals(response.isProfileFormDefined(), isProfileFormDefined);

		assertEquals(response.getKeyColumns().size(), 1);
		assertEquals(response.getKeyColumns().get(0), keyColumn);

		assertEquals(response.getColumns().size(), 3);
		assertEquals(response.getColumns().get(0).getName(), column1);
		assertEquals(response.getColumns().get(1).getName(), column2);
		assertEquals(response.getColumns().get(1).getSelectionValues().size(), 2);
		assertEquals(response.getColumns().get(1).getSelectionValues().get(0), column2_value1);
		assertEquals(response.getColumns().get(1).getSelectionValues().get(1), column2_value2);
		assertEquals(response.getColumns().get(2).getName(), column3);
		assertEquals(response.getColumns().get(2).getDefaultValue(), column3_default);
		assertEquals(response.getColumns().get(2).getType().value(), column3_type);
	}
}
