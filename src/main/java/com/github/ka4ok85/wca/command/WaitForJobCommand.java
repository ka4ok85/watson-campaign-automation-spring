package com.github.ka4ok85.wca.command;

import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.constants.JobStatus;
import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.InternalApiMismatchException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.JobOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.sftp.SFTP;

public class WaitForJobCommand extends AbstractCommand<JobResponse, JobOptions> {

	private static String apiMethodName = "GetJobStatus";

	private static final Logger log = LoggerFactory.getLogger(WaitForJobCommand.class);

	public WaitForJobCommand(OAuthClient oAuthClient, SFTP sftp) {
		super(oAuthClient, sftp);
	}

	@Override
	public ResponseContainer<JobResponse> executeCommand(JobOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "JobOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element jobID = doc.createElement("JOB_ID");
		jobID.setTextContent(String.valueOf(options.getJobId()));
		addChildNode(jobID, currentNode);

		String xml = getXML();
		log.trace("Sending request to {} API. XML: {}", apiMethodName, xml);

		log.debug("Running {} API for Job ID {}", apiMethodName, options.getJobId());
		Node resultNode = runApi(xml);

		log.trace("Received response from {} API. Result Node XML: {}", apiMethodName, resultNode.getTextContent());

		XPathFactory factory = XPathFactory.newInstance();
    	XPath xpath = factory.newXPath();

    	JobResponse jobResponse = new JobResponse();
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);
			Node jobStatusNode = (Node) xpath.evaluate("JOB_STATUS", resultNode, XPathConstants.NODE);
			Node jobDescriptionNode = (Node) xpath.evaluate("JOB_DESCRIPTION", resultNode, XPathConstants.NODE);

			if (options.getJobId() != Integer.valueOf(jobIdNode.getTextContent())) {
				throw new InternalApiMismatchException("Bad Job ID received from GetJobStatus API. Should have been " + options.getJobId() + ", but received " + jobIdNode.getTextContent());
			}

			jobResponse.setJobId(options.getJobId());
			jobResponse.setJobDescription(jobDescriptionNode.getTextContent());
			jobResponse.setJobStatus(JobStatus.valueOf(jobStatusNode.getTextContent()));
		} catch (XPathExpressionException | InternalApiMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseContainer<JobResponse> response = new ResponseContainer<JobResponse>(jobResponse);

		return response;
	}
}
