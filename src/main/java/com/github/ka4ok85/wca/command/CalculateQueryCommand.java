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
import com.github.ka4ok85.wca.options.CalculateQueryOptions;
import com.github.ka4ok85.wca.response.CalculateQueryResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class CalculateQueryCommand extends AbstractCommand<CalculateQueryResponse, CalculateQueryOptions> {

	private static final String apiMethodName = "CalculateQuery";
	private static final Logger log = LoggerFactory.getLogger(CalculateQueryCommand.class);

	@Autowired
	private CalculateQueryResponse calculateQueryResponse;

	@Override
	public void buildXmlRequest(CalculateQueryOptions options) {
		Objects.requireNonNull(options, "CalculateQueryOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element queryId = doc.createElement("QUERY_ID");
		queryId.setTextContent(options.getQueryId().toString());
		addChildNode(queryId, currentNode);
	}

	@Override
	public ResponseContainer<CalculateQueryResponse> readResponse(Node resultNode, CalculateQueryOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Long jobId;
		String description;
		String listName;
		Long listSize;
		Long parentListId;
		String parentListName;
		String parentListType;
		Long queryExpressionId;
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);

			jobId = Long.parseLong(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);

			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (jobResponse.isComplete()) {
				log.debug("Job is completed");
				description = jobResponse.getJobDescription();
				listName = jobResponse.getParameters().get("LIST_NAME");
				listSize = Long.parseLong(jobResponse.getParameters().get("LIST_SIZE"));
				parentListId = Long.parseLong(jobResponse.getParameters().get("PARENT_LIST_ID"));
				parentListName = jobResponse.getParameters().get("PARENT_LIST_NAME");
				parentListType = jobResponse.getParameters().get("PARENT_LIST_TYPE");
				queryExpressionId = Long.parseLong(jobResponse.getParameters().get("QUERY_EXPRESSION_ID"));
			} else {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is "
						+ jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		calculateQueryResponse.setJobId(jobId);
		calculateQueryResponse.setDescription(description);
		calculateQueryResponse.setListName(listName);
		calculateQueryResponse.setListSize(listSize);
		calculateQueryResponse.setParentListId(parentListId);
		calculateQueryResponse.setParentListName(parentListName);
		calculateQueryResponse.setParentListType(parentListType);
		calculateQueryResponse.setQueryExpressionId(queryExpressionId);
		ResponseContainer<CalculateQueryResponse> response = new ResponseContainer<CalculateQueryResponse>(
				calculateQueryResponse);

		return response;
	}
}
