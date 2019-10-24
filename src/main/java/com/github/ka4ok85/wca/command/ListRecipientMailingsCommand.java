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

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.ListRecipientMailingsOptions;
import com.github.ka4ok85.wca.response.ListRecipientMailingsResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.RecipientMailing;

/**
 * <strong>Class for interacting with WCA ListRecipientMailings API.</strong> It
 * builds XML request for ListRecipientMailings API using
 * {@link com.github.ka4ok85.wca.options.ListRecipientMailingsOptions} and reads
 * response into
 * {@link com.github.ka4ok85.wca.response.ListRecipientMailingsResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class ListRecipientMailingsCommand
		extends AbstractInstantCommand<ListRecipientMailingsResponse, ListRecipientMailingsOptions> {

	private static final String apiMethodName = "ListRecipientMailings";

	@Autowired
	private ListRecipientMailingsResponse listRecipientMailingsResponse;

	/**
	 * Builds XML request for ListRecipientMailings API using
	 * {@link com.github.ka4ok85.wca.options.ListRecipientMailingsOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(ListRecipientMailingsOptions options) {
		Objects.requireNonNull(options, "ListRecipientMailingsOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element recipientID = doc.createElement("RECIPIENT_ID");
		recipientID.setTextContent(options.getRecipientId().toString());
		addChildNode(recipientID, currentNode);
	}

	/**
	 * Reads ListRecipientMailings API response into
	 * {@link com.github.ka4ok85.wca.response.ListRecipientMailingsResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO ListRecipientMailingsResponse
	 */
	@Override
	public ResponseContainer<ListRecipientMailingsResponse> readResponse(Node resultNode,
			ListRecipientMailingsOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		List<RecipientMailing> mailings = new ArrayList<RecipientMailing>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		try {
			NodeList mailingsNode = (NodeList) xpath.evaluate("Mailing", resultNode, XPathConstants.NODESET);
			Node mailingNode;
			for (int i = 0; i < mailingsNode.getLength(); i++) {
				RecipientMailing mailing = new RecipientMailing();
				mailingNode = mailingsNode.item(i);

				mailing.setMailingId(Long.parseLong(
						((Node) xpath.evaluate("MailingId", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setMailingName(
						((Node) xpath.evaluate("MailingName ", mailingNode, XPathConstants.NODE)).getTextContent());
				mailing.setSentDateTime(LocalDateTime.parse(
						((Node) xpath.evaluate("SentTS", mailingNode, XPathConstants.NODE)).getTextContent(),
						formatter));
				mailing.setTotalAttachments(
						Long.parseLong(((Node) xpath.evaluate("TotalAttachments", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setTotalBounces(Long.parseLong(
						((Node) xpath.evaluate("TotalBounces", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setTotalClicks(Long.parseLong(
						((Node) xpath.evaluate("TotalClicks", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setTotalClickstreams(
						Long.parseLong(((Node) xpath.evaluate("TotalClickstreams", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setTotalConversions(
						Long.parseLong(((Node) xpath.evaluate("TotalConversions", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setTotalForwards(Long.parseLong(
						((Node) xpath.evaluate("TotalForwards", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setTotalMediaPlays(Long.parseLong(
						((Node) xpath.evaluate("TotalMediaPlays", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setTotalOpens(Long.parseLong(
						((Node) xpath.evaluate("TotalOpens", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setTotalOptOuts(Long.parseLong(
						((Node) xpath.evaluate("TotalOptOuts", mailingNode, XPathConstants.NODE)).getTextContent()));

				mailings.add(mailing);
			}

			listRecipientMailingsResponse.setMailings(mailings);
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		ResponseContainer<ListRecipientMailingsResponse> response = new ResponseContainer<ListRecipientMailingsResponse>(
				listRecipientMailingsResponse);

		return response;
	}

}
