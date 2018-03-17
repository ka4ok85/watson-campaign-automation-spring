package com.github.ka4ok85.wca;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.pod.Pod;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.sftp.SFTP;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.command.AbstractCommand;
import com.github.ka4ok85.wca.command.ExportListCommand;
import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.oauth.OAuthClientImplementation;

public class Engage {
	final String grantType = "refresh_token";

	private int podNumber;
	private String clientId;
	private String clientSecret;
	private String refreshToken;
	private String accessUrl;
	private String accessToken;
	private OAuthClient oAuthClient;
	private SFTP sftp;

	private static final Logger log = LoggerFactory.getLogger(Engage.class);

	public Engage(int podNumber, String clientId, String clientSecret, String refreshToken) {
		this.podNumber = podNumber;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
		this.oAuthClient = new OAuthClientImplementation(podNumber, clientId, clientSecret, refreshToken);
		this.sftp = new SFTP(this.oAuthClient);
	}
/*
	public ResponseContainer<AbstractResponse> run(Class<?> clazz, AbstractOptions options) throws FailedGetAccessTokenException {
			OAuthClient oAuthClient = new OAuthClientImplementation(podNumber, clientId, clientSecret, refreshToken);
			accessToken = oAuthClient.getAccessToken();
			String name = clazz.getName();
			Constructor<?> constructor;
			try {
				constructor = clazz.getConstructor();
				Class<? extends Constructor> x = constructor.getClass();
				System.out.println("Q:             " + x);
				AbstractCommand<AbstractResponse> command = (AbstractCommand<AbstractResponse>) constructor.newInstance();
				command.setoAuthClient(oAuthClient);
				ResponseContainer<AbstractResponse> response = command.executeCommand(options);
				System.out.println("-------------output response----------");
				System.out.println(response.getResposne());
				
				return response;

			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return null;
	}
	*/
	public ResponseContainer<ExportListResponse> exportList(ExportListOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		//ResponseContainer<AbstractResponse> res = run(ExportListCommand.class, options);
		//ResponseContainer<ExportListResponse> result = new ResponseContainer<ExportListResponse>((ExportListResponse) res.getResposne());
		ExportListCommand command = new ExportListCommand(this.oAuthClient, this.sftp); 
		ResponseContainer<ExportListResponse> result = command.executeCommand(options);

		
		return result;
	}
	/*
	public ResponseContainer<JobResponse> waitForJob(JobOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		WaitForJobCommand command = new WaitForJobCommand(this.oAuthClient, this); 
		ResponseContainer<JobResponse> result = command.executeCommand(options);
	}
	*/
}
