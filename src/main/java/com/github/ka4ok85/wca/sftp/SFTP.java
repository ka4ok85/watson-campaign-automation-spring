package com.github.ka4ok85.wca.sftp;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.el.lang.ELArithmetic;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.pod.Pod;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;



public class SFTP {
	private static int port = 22;
	private static String username = "oauth";

	private OAuthClient oAuthClient;

	public SFTP(OAuthClient oAuthClient) {
		this.oAuthClient = oAuthClient;
	}


	public void download(String filePath, String localAbsoluteFilePath) {

			try {
				JSch jsch = new JSch();
				Session session = jsch.getSession(username, Pod.getSFTPHostName(this.oAuthClient.getPodNumber()), port);
				try {
					session.setPassword(this.oAuthClient.getAccessToken());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("int ex: " + e.getMessage());
					System.out.println("int ex s: " + e.getStackTrace());
					e.printStackTrace();
				}
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.connect();
				Channel channel = session.openChannel("sftp");
				channel.connect();
				ChannelSftp channelSftp = (ChannelSftp) channel;
				channelSftp.cd("download");
				channelSftp.get(filePath, localAbsoluteFilePath);
				channelSftp.exit();
				session.disconnect();
				//return channelSftp; 
			} catch (JSchException | SftpException e11) {
				System.out.println("ex: " + e11.getMessage());
				System.out.println("ex s: " + e11.getStackTrace());
				e11.printStackTrace();
			}
		
	}
}
