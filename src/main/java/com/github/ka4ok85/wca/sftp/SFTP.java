package com.github.ka4ok85.wca.sftp;

import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.pod.Pod;
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
			session.setPassword(this.oAuthClient.getAccessToken());
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
		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		} catch (FailedGetAccessTokenException e) {
			e.printStackTrace();
		}
	}
}
