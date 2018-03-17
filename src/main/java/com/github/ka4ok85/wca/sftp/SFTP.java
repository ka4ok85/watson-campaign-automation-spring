package com.github.ka4ok85.wca.sftp;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.pod.Pod;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTP {
	private static int port = 22;
	private static String username = "oauth";

	private Session session;
	private Channel channel;
	private ChannelSftp channelSftp;

	public SFTP(OAuthClient oAuthClient) {
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, Pod.getSFTPHostName(oAuthClient.getPodNumber()), port);
			session.setPassword(oAuthClient.getAccessToken());
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ChannelSftp getChannelSftp() {
		try {
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;

			return channelSftp;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return channelSftp;
	}

	public void close() {
		channelSftp.exit();
		session.disconnect();
	}

}
