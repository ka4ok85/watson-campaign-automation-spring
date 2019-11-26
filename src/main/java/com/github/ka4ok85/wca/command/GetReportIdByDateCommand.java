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
import com.github.ka4ok85.wca.options.GetReportIdByDateOptions;
import com.github.ka4ok85.wca.response.GetReportIdByDateResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.ReportIdByDateMailing;

/**
 * <strong>Class for interacting with WCA GetReportIdByDate API.</strong> It
 * builds XML request for GetReportIdByDate API using
 * {@link com.github.ka4ok85.wca.options.GetReportIdByDateOptions} and reads
 * response into
 * {@link com.github.ka4ok85.wca.response.GetReportIdByDateResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class GetReportIdByDateCommand
		extends AbstractInstantCommand<GetReportIdByDateResponse, GetReportIdByDateOptions> {

	private static final String apiMethodName = "GetReportIdByDate";

	@Autowired
	private GetReportIdByDateResponse getReportIdByDateResponse;

	/**
	 * Builds XML request for GetReportIdByDate API using
	 * {@link com.github.ka4ok85.wca.options.GetReportIdByDateOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(GetReportIdByDateOptions options) {
		Objects.requireNonNull(options, "GetReportIdByDateOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element mailingId = doc.createElement("MAILING_ID");
		mailingId.setTextContent(options.getMailingId().toString());
		addChildNode(mailingId, currentNode);

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

		Element dateStart = doc.createElement("DATE_START");
		dateStart.setTextContent(options.getDateStart().format(formatter));
		addChildNode(dateStart, currentNode);

		Element dateEnd = doc.createElement("DATE_END");
		dateEnd.setTextContent(options.getDateEnd().format(formatter));
		addChildNode(dateEnd, currentNode);
	}

	/**
	 * Reads GetReportIdByDate API response into
	 * {@link com.github.ka4ok85.wca.response.GetReportIdByDateResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO GetReportIdByDate Response
	 */
	@Override
	public ResponseContainer<GetReportIdByDateResponse> readResponse(Node resultNode,
			GetReportIdByDateOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		List<ReportIdByDateMailing> mailings = new ArrayList<ReportIdByDateMailing>();
		final DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		try {

			NodeList mailingsNode = (NodeList) xpath.evaluate("Mailing", resultNode, XPathConstants.NODESET);
			Node mailingNode;

			for (int i = 0; i < mailingsNode.getLength(); i++) {
				ReportIdByDateMailing mailingResponse = new ReportIdByDateMailing();
				mailingNode = mailingsNode.item(i);
				mailingResponse.setReportId(Long.parseLong(
						((Node) xpath.evaluate("ReportId", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailingResponse.setSentDateTime(LocalDateTime.parse(
						((Node) xpath.evaluate("SentTS", mailingNode, XPathConstants.NODE)).getTextContent(),
						responseFormatter));

				mailings.add(mailingResponse);
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		getReportIdByDateResponse.setMailings(mailings);

		ResponseContainer<GetReportIdByDateResponse> response = new ResponseContainer<GetReportIdByDateResponse>(
				getReportIdByDateResponse);

		return response;
	}
}