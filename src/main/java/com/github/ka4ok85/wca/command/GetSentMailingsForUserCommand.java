package com.github.ka4ok85.wca.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.GetSentMailingsForUserOptions;
import com.github.ka4ok85.wca.response.GetSentMailingsForUserResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.SentMailing;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@Service
@Scope("prototype")
public class GetSentMailingsForUserCommand
		extends AbstractCommand<GetSentMailingsForUserResponse, GetSentMailingsForUserOptions> {

	private static final String apiMethodName = "GetSentMailingsForUser";

	@Autowired
	private GetSentMailingsForUserResponse getSentMailingsForUserResponse;

	@Override
	public void buildXmlRequest(GetSentMailingsForUserOptions options) {
		Objects.requireNonNull(options, "GetSentMailingsForUserOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		DateTimeRange dateTimeRange = options.getDateTimeRange();
		addParameter(currentNode, "DATE_START", dateTimeRange.getFormattedStartDateTime());
		addParameter(currentNode, "DATE_END", dateTimeRange.getFormattedEndDateTime());

		if (options.getVisibility() == Visibility.SHARED) {
			addBooleanParameter(currentNode, "SHARED", true);
		} else if (options.getVisibility() == Visibility.PRIVATE) {
			addBooleanParameter(currentNode, "PRIVATE", true);
		}

		if (options.getOptionalUser() != null) {
			addParameter(currentNode, "OPTIONALUSER", options.getOptionalUser());
		}

		if (options.isMailingCountOnly()) {
			addBooleanParameter(currentNode, "MAILING_COUNT_ONLY", true);
		}

		if (options.isAutomated()) {
			addBooleanParameter(currentNode, "AUTOMATED", true);
		}

		if (options.isCampaignActive()) {
			addBooleanParameter(currentNode, "CAMPAIGN_ACTIVE", true);
		}

		if (options.isCampaignCancelled()) {
			addBooleanParameter(currentNode, "CAMPAIGN_CANCELLED", true);
		}

		if (options.isCampaignCompleted()) {
			addBooleanParameter(currentNode, "CAMPAIGN_COMPLETED", true);
		}

		if (options.isCampaignScrapeTemplate()) {
			addBooleanParameter(currentNode, "CAMPAIGN_SCRAPE_TEMPLATE", true);
		}

		if (options.isExcludeTestMailings()) {
			addBooleanParameter(currentNode, "EXCLUDE_TEST_MAILINGS", true);
		}

		if (options.isExcludeZeroSent()) {
			addBooleanParameter(currentNode, "EXCLUDE_ZERO_SENT", true);
		}

		if (options.isIncludeTags()) {
			addBooleanParameter(currentNode, "INCLUDE_TAGS", true);
		}

		if (options.isOptinConfirmation()) {
			addBooleanParameter(currentNode, "OPTIN_CONFIRMATION", true);
		}

		if (options.isProfileConfirmation()) {
			addBooleanParameter(currentNode, "PROFILE_CONFIRMATION", true);
		}

		if (options.isScheduled()) {
			addBooleanParameter(currentNode, "SCHEDULED", true);
		}

		if (options.isSend()) {
			addBooleanParameter(currentNode, "SENT", true);
		}

		if (options.isSending()) {
			addBooleanParameter(currentNode, "SENDING", true);
		}
	}

	@Override
	public ResponseContainer<GetSentMailingsForUserResponse> readResponse(Node resultNode,
			GetSentMailingsForUserOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		List<SentMailing> sentMailings = new ArrayList<SentMailing>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
		try {

			if (options.isMailingCountOnly()) {
				getSentMailingsForUserResponse.setSentMailingsCount(
						Long.parseLong(((Node) xpath.evaluate("SentMailingsCount", resultNode, XPathConstants.NODE))
								.getTextContent()));
			} else {
				NodeList mailingsNode = (NodeList) xpath.evaluate("Mailing", resultNode, XPathConstants.NODESET);
				Node mailingNode;

				for (int i = 0; i < mailingsNode.getLength(); i++) {
					SentMailing mailing = new SentMailing();
					mailingNode = mailingsNode.item(i);

					mailing.setListId(Long.parseLong(
							((Node) xpath.evaluate("ListId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setListName(
							((Node) xpath.evaluate("ListName", mailingNode, XPathConstants.NODE)).getTextContent());
					mailing.setMailingId(Long.parseLong(
							((Node) xpath.evaluate("MailingId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setMailingName(
							((Node) xpath.evaluate("MailingName", mailingNode, XPathConstants.NODE)).getTextContent());
					mailing.setNumSent(Long.parseLong(
							((Node) xpath.evaluate("NumSent", mailingNode, XPathConstants.NODE)).getTextContent()));
					Node parentListIdNode = (Node) xpath.evaluate("ParentListId", mailingNode, XPathConstants.NODE);
					if (parentListIdNode != null) {
						mailing.setParentListId(Long.parseLong(parentListIdNode.getTextContent()));
					}

					mailing.setParentTemplateId(
							Long.parseLong(((Node) xpath.evaluate("ParentTemplateId", mailingNode, XPathConstants.NODE))
									.getTextContent()));
					mailing.setReportId(Long.parseLong(
							((Node) xpath.evaluate("ReportId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setScheduledDateTime(LocalDateTime.parse(
							((Node) xpath.evaluate("ScheduledTS", mailingNode, XPathConstants.NODE)).getTextContent(),
							formatter));
					mailing.setSentDateTime(LocalDateTime.parse(
							((Node) xpath.evaluate("SentTS", mailingNode, XPathConstants.NODE)).getTextContent(),
							formatter));
					mailing.setSubject(
							((Node) xpath.evaluate("Subject", mailingNode, XPathConstants.NODE)).getTextContent());
					mailing.setUserName(
							((Node) xpath.evaluate("UserName", mailingNode, XPathConstants.NODE)).getTextContent());
					mailing.setVisibility(Visibility.getVisibilityByAlias(
							((Node) xpath.evaluate("Visibility", mailingNode, XPathConstants.NODE)).getTextContent()));

					NodeList tagsNode = (NodeList) xpath.evaluate("Tags/Tag", mailingNode, XPathConstants.NODESET);
					List<String> tagsList = new ArrayList<String>();
					for (int j = 0; j < tagsNode.getLength(); j++) {
						tagsList.add(tagsNode.item(j).getTextContent());
					}

					mailing.setTags(tagsList);

					sentMailings.add(mailing);
				}

				getSentMailingsForUserResponse.setSentMailings(sentMailings);
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		ResponseContainer<GetSentMailingsForUserResponse> response = new ResponseContainer<GetSentMailingsForUserResponse>(
				getSentMailingsForUserResponse);

		return response;
	}
}