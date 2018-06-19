package com.github.ka4ok85.wca.command;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.processor.JobProcessor;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@Service
abstract public class AbstractJobCommand<T extends AbstractResponse, V extends AbstractOptions>
		extends AbstractCommand<T, V> {

	public abstract ResponseContainer<T> readResponse(JobPollingContainer jobPollingContainer, JobResponse jobResponse,
			V options);

	@Retryable(value = EngageApiException.class, maxAttempts = 5, backoff = @Backoff(delay = 5000, multiplier = 2))
	public ResponseContainer<T> executeCommand(V options) {
		buildXmlRequest(options);
		String xml = getXML();
		log.debug("XML Request is {}", xml);

		Node resultNode = runApi(xml);

		JobPollingContainer jobPollingContainer = readStartPollingResponse(resultNode);

		JobResponse jobResponse = JobProcessor.waitUntilJobIsCompleted(jobPollingContainer.getJobId(), oAuthClient,
				sftp);

		return readResponse(jobPollingContainer, jobResponse, options);
	}

	private JobPollingContainer readStartPollingResponse(Node resultNode) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Long jobId;
		Map<String, String> parameters = new HashMap<String, String>();
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);

			NodeList parametersNode = (NodeList) xpath.evaluate("*", resultNode, XPathConstants.NODESET);
			Node parameterNode;
			String nodeName;
			String nodeValue;
			for (int i = 0; i < parametersNode.getLength(); i++) {
				parameterNode = parametersNode.item(i);
				nodeName = parameterNode.getNodeName();
				nodeValue = parameterNode.getTextContent();
				if (!nodeName.equals("SUCCESS") && !nodeName.equals("JOB_ID")) {
					parameters.put(nodeName, nodeValue);
				}
			}

			jobId = Long.parseLong(jobIdNode.getTextContent());
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new BadApiResultException(e.getMessage());
		}

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		jobPollingContainer.setJobId(jobId);
		jobPollingContainer.setParameters(parameters);

		return jobPollingContainer;
	}
}
