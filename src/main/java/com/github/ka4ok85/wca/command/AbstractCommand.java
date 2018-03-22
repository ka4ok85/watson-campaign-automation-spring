package com.github.ka4ok85.wca.command;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.options.JobOptions;
import com.github.ka4ok85.wca.pod.Pod;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.sftp.SFTP;

@Service
public abstract class AbstractCommand<T extends AbstractResponse, V extends AbstractOptions> {
	protected OAuthClient oAuthClient;
	protected SFTP sftp;
	protected Document doc;
	protected Node currentNode;

	private final int maxExecutionTime = 86400;
	private final int jobCheckInterval = 10;
	
	private static final Logger log = LoggerFactory.getLogger(AbstractCommand.class);

	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			currentNode = doc;

			Element rootElement = doc.createElement("Envelope");
			Element bodyElement = doc.createElement("Body");
			currentNode = addChildNode(rootElement, null);
			currentNode = addChildNode(bodyElement, null);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public AbstractCommand() {

	}	

	public void setoAuthClient(OAuthClient oAuthClient) {
		this.oAuthClient = oAuthClient;
	}

	public void setSftp(SFTP sftp) {
		this.sftp = sftp;
	}

	public ResponseContainer<T> executeCommand(V options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		System.out.println("Running Command with options " + options.getClass());

		return new ResponseContainer<T>(null);
	}

	protected String getXML() {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writer.toString();
	}

	protected Node addChildNode(Node childNode, Node parentNode) {
		if (parentNode == null) {
			this.currentNode.appendChild(childNode);
		} else {
			parentNode.appendChild(childNode);
		}

		return childNode;
	}

	protected Node addBooleanParameter(Node parentNode, String name, boolean value) {
		String apiValue;
		if (true == value) {
			apiValue = "TRUE";
		} else {
			apiValue = "FALSE";
		}

		Node node = doc.createElement(name);
		node.setTextContent(apiValue);

		return addChildNode(node, parentNode);
	}

	protected Node addParameter(Node parentNode, String name, String value) {
		Node node = doc.createElement(name);
		node.setTextContent(value);

		return addChildNode(node, parentNode);
	}

	protected Node runApi(String xml) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		// TODO UTF8 check
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + oAuthClient.getAccessToken());
        headers.setContentType(MediaType.TEXT_XML);
        headers.setContentLength(xml.length());
        HttpEntity<String> entity = new HttpEntity<String>(xml, headers);
        Node resultNode = null;

        try {
        	RestTemplate restTemplate = new RestTemplate();
        	ResponseEntity<String> result = restTemplate.exchange(Pod.getXMLAPIEndpoint(oAuthClient.getPodNumber()), HttpMethod.POST, entity, String.class);
        
        	try {
	        	DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        	InputSource is = new InputSource();
	        	is.setCharacterStream(new StringReader(result.getBody()));

	        	Document doc = db.parse(is);

	        	XPathFactory factory = XPathFactory.newInstance();
	        	XPath xpath = factory.newXPath();

	        	Node successNode = (Node) xpath.evaluate("/Envelope/Body/RESULT/SUCCESS", doc, XPathConstants.NODE);
	        	
	        	boolean apiResult = Boolean.parseBoolean(successNode.getTextContent());
	        	if (apiResult == false) {
	        		Node faultStringNode = (Node) xpath.evaluate("/Envelope/Body/Fault/FaultString", doc, XPathConstants.NODE);
	        		throw new FaultApiResultException(faultStringNode.getTextContent());
	        	}
	        	
	        	resultNode = (Node) xpath.evaluate("/Envelope/Body/RESULT", doc, XPathConstants.NODE);
        	} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
        		throw new BadApiResultException(e.getMessage());
        	}
        	
        } catch (HttpClientErrorException e) {
        	throw new BadApiResultException(e.getMessage());
        }

        return resultNode;
	}

	protected JobResponse waitUntilJobIsCompleted(int jobId) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException, JobBadStateException {
		final WaitForJobCommand command = new WaitForJobCommand();
		command.setoAuthClient(oAuthClient);
		command.setSftp(sftp);
		final JobOptions options = new JobOptions(jobId);

		int currentApiExecutionTime = 0;
		while (true) {
			ResponseContainer<JobResponse> result = command.executeCommand(options);
			JobResponse response = result.getResposne();

			log.debug("Current Execution Time for JOB ID {} is {} seconds", jobId, currentApiExecutionTime);
			if (response.isError()) {
				// TODO: access error file
				throw new JobBadStateException("WaitForJobCommand failure");
			}

			if (response.isCanceled()) {
				throw new JobBadStateException("Job was canceled!");
			}

			if (response.isRunning() || response.isWaiting()) {
				try {
					Thread.sleep(jobCheckInterval*1000);
				} catch (InterruptedException e) {
					throw new EngageApiException(e.getMessage());
				}
			} else {
				return response;
			}

			currentApiExecutionTime = currentApiExecutionTime + jobCheckInterval;			
			if (currentApiExecutionTime > maxExecutionTime) {
				break;
			}
		}

		return null;
	}
}