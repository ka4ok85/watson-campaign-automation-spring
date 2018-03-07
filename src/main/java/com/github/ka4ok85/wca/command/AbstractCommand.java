package com.github.ka4ok85.wca.command;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

public abstract class AbstractCommand<T extends AbstractResponse, V extends AbstractOptions> {
	protected OAuthClient oAuthClient;
	protected Document doc;
	protected Node currentNode;

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

	public AbstractCommand(OAuthClient oAuthClient) {
		this.oAuthClient = oAuthClient;
	}

	public ResponseContainer<T> executeCommand(V options) {
		System.out.println("Running Command with options " + options.getClass());

		return new ResponseContainer<T>(null);
	}

	public void setoAuthClient(OAuthClient oAuthClient) {
		this.oAuthClient = oAuthClient;
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

	protected Node addParameter(Node parentNode, String name, boolean value) {
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

}