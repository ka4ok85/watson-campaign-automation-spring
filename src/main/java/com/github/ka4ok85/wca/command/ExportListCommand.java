package com.github.ka4ok85.wca.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.Engage;
import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;

public class ExportListCommand extends AbstractCommand<ExportListResponse, ExportListOptions> {

	public ExportListCommand(OAuthClient oAuthClient) {
		super(oAuthClient);
	}

	private static String apiMethodName = "ExportList";

	@Override
	public ResponseContainer<ExportListResponse> executeCommand(ExportListOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		System.out.println("Running ExportListCommand with options " + options.getClass());

		Objects.requireNonNull(options, "ExportListOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element exportType = doc.createElement("EXPORT_TYPE");
		exportType.setTextContent(options.getExportType().value());
		addChildNode(exportType, currentNode);

		Element exportFormat = doc.createElement("EXPORT_FORMAT");
		exportFormat.setTextContent(options.getExportFormat().value());
		addChildNode(exportFormat, currentNode);

		Element fileEncoding = doc.createElement("FILE_ENCODING");
		fileEncoding.setTextContent(options.getFileEncoding().value());
		addChildNode(fileEncoding, currentNode);

		DateTimeRange lastModifiedRange = options.getLastModifiedRange();
		if (lastModifiedRange != null) {
			addParameter(currentNode, "DATE_START", lastModifiedRange.getFormattedStartDateTime());
			addParameter(currentNode, "DATE_END", lastModifiedRange.getFormattedEndDateTime());
		}

		addBooleanParameter(methodElement, "ADD_TO_STORED_FILES", options.isAddToStoredFiles());
		addBooleanParameter(methodElement, "INCLUDE_LEAD_SOURCE", options.isIncludeLeadSource());
		addBooleanParameter(methodElement, "INCLUDE_LIST_ID_IN_FILE", options.isIncludeListId());
		addBooleanParameter(methodElement, "INCLUDE_RECIPIENT_ID", options.isIncludeRecipientId());

		if (options.getExportColumns() != null && options.getExportColumns().size() > 0) {
			Element exportColumns = doc.createElement("EXPORT_COLUMNS");
			addChildNode(exportColumns, currentNode);
			Element columnElement;
			for (String column : options.getExportColumns()) {
				columnElement = doc.createElement("COLUMN");
				columnElement.setTextContent(column);
				addChildNode(columnElement, exportColumns);
			}
		}

		String xml = getXML();
		//System.out.println(xml);

		Node resultNode = runApi(xml);
		
    	XPathFactory factory = XPathFactory.newInstance();
    	XPath xpath = factory.newXPath();

		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);
			Node filePathNode = (Node) xpath.evaluate("FILE_PATH", resultNode, XPathConstants.NODE);
			
			System.out.println(jobIdNode.getTextContent());
			System.out.println(filePathNode.getTextContent());
			
			
			JobResponse jobResponse = waitUntilJobIsCompleted(Integer.parseInt(jobIdNode.getTextContent()));
			if (jobResponse.isComplete()) {
				String filePath = filePathNode.getTextContent();
				// access result file
				if (options.getLocalAbsoluteFilePath() != null) {
					System.out.println("file:" + filePath);
					
					String username = "oauth";
					String password = oAuthClient.getAccessToken();
					String hostname = "transfer0.silverpop.com";
					int port = 22;
					
					Session     session     = null;
			        Channel     channel     = null;
			        ChannelSftp channelSftp = null;

			        try{
			            JSch jsch = new JSch();
			            session = jsch.getSession(username,hostname,port);
			            session.setPassword(password);
			            java.util.Properties config = new java.util.Properties();
			            config.put("StrictHostKeyChecking", "no");
			            session.setConfig(config);
			            session.connect();
			            channel = session.openChannel("sftp");
			            channel.connect();
			            channelSftp = (ChannelSftp)channel;
			            channelSftp.cd("download");
			            channelSftp.get(filePath, options.getLocalAbsoluteFilePath());
			            channelSftp.exit();
			            session.disconnect();
			        }catch(Exception ex){
			            ex.printStackTrace();
			        }
				}

			} else {
				// TODO exception?
			}
			System.out.println(jobResponse);
			

		} catch (XPathExpressionException | JobBadStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(resultNode);

		ExportListResponse exportListResponse = new ExportListResponse();
		ResponseContainer<ExportListResponse> response = new ResponseContainer<ExportListResponse>(exportListResponse);

		System.out.println("End ExportListCommand");

		return response;
	}



}
