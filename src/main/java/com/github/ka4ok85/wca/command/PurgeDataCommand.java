package com.github.ka4ok85.wca.command;

import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.PurgeDataOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.PurgeDataResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class PurgeDataCommand extends AbstractCommand<PurgeDataResponse, PurgeDataOptions> {

	private static final String apiMethodName = "PurgeData";
	private static final Logger log = LoggerFactory.getLogger(PurgeDataCommand.class);

	@Autowired
	private PurgeDataResponse purgeDataResponse;

	@Override
	public void buildXmlRequest(PurgeDataOptions options) {
		Objects.requireNonNull(options, "PurgeDataOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element targetId = doc.createElement("TARGET_ID");
		targetId.setTextContent(options.getTargetId().toString());
		addChildNode(targetId, currentNode);

		Element sourceId = doc.createElement("SOURCE_ID");
		sourceId.setTextContent(options.getSourceId().toString());
		addChildNode(sourceId, currentNode);
	}

	@Override
	public ResponseContainer<PurgeDataResponse> readResponse(Node resultNode, PurgeDataOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String description;
		String purgedContactListName;
		Long purgedContactListId;
		Long purgedContactListSize;
		String targetListName;
		String sourceListName;

		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);

			final Long jobId = Long.parseLong(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);

			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (jobResponse.isComplete()) {
				description = jobResponse.getJobDescription();
				purgedContactListId = Long.parseLong(jobResponse.getParameters().get("LIST_ID_C"));
				purgedContactListName = jobResponse.getParameters().get("LIST_NAME_C");
				purgedContactListSize = Long.parseLong(jobResponse.getParameters().get("LIST_SIZE"));
				sourceListName = jobResponse.getParameters().get("LIST2_NAME");
				targetListName = jobResponse.getParameters().get("LIST1_NAME");
			} else {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is "
						+ jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		purgeDataResponse.setDescription(description);
		purgeDataResponse.setPurgedContactListId(purgedContactListId);
		purgeDataResponse.setPurgedContactListName(purgedContactListName);
		purgeDataResponse.setPurgedContactListSize(purgedContactListSize);
		purgeDataResponse.setSourceListName(sourceListName);
		purgeDataResponse.setTargetListName(targetListName);

		ResponseContainer<PurgeDataResponse> response = new ResponseContainer<PurgeDataResponse>(purgeDataResponse);

		return response;
	}
}
