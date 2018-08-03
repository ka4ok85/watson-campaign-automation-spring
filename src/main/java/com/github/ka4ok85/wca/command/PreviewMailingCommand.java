package com.github.ka4ok85.wca.command;

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

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.PreviewMailingOptions;
import com.github.ka4ok85.wca.response.PreviewMailingResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA PreviewMailing API.</strong> It builds
 * XML request for PreviewMailing API using
 * {@link com.github.ka4ok85.wca.options.PreviewMailingOptions} and reads
 * response into {@link com.github.ka4ok85.wca.response.PreviewMailingResponse}.
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
public class PreviewMailingCommand extends AbstractInstantCommand<PreviewMailingResponse, PreviewMailingOptions> {

	private static final String apiMethodName = "PreviewMailing";

	@Autowired
	private PreviewMailingResponse previewMailingResponse;

	/**
	 * Builds XML request for PreviewMailing API using
	 * {@link com.github.ka4ok85.wca.options.PreviewMailingOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(PreviewMailingOptions options) {
		Objects.requireNonNull(options, "PreviewMailingOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element mailingId = doc.createElement("MailingId");
		mailingId.setTextContent(options.getMailingId().toString());
		addChildNode(mailingId, currentNode);

		if (options.getRecipientEmail() != null) {
			Element recipientEmail = doc.createElement("RecipientEmail");
			recipientEmail.setTextContent(options.getRecipientEmail());
			addChildNode(recipientEmail, currentNode);
		}
	}

	/**
	 * Reads PreviewMailing API response into
	 * {@link com.github.ka4ok85.wca.response.PreviewMailingResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO PreviewMailing Response
	 */
	@Override
	public ResponseContainer<PreviewMailingResponse> readResponse(Node resultNode, PreviewMailingOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		String htmlBody;
		String aolBody;
		String textBody;
		String spamScore;
		try {
			htmlBody = ((Node) xpath.evaluate("HTMLBody", resultNode, XPathConstants.NODE)).getTextContent();
			aolBody = ((Node) xpath.evaluate("AOLBody", resultNode, XPathConstants.NODE)).getTextContent();
			textBody = ((Node) xpath.evaluate("TextBody", resultNode, XPathConstants.NODE)).getTextContent();
			spamScore = ((Node) xpath.evaluate("SPAMScore", resultNode, XPathConstants.NODE)).getTextContent();
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		previewMailingResponse.setAolBody(aolBody);
		previewMailingResponse.setHtmlBody(htmlBody);
		previewMailingResponse.setSpamScore(spamScore);
		previewMailingResponse.setTextBody(textBody);

		ResponseContainer<PreviewMailingResponse> response = new ResponseContainer<PreviewMailingResponse>(
				previewMailingResponse);

		return response;
	}
}
