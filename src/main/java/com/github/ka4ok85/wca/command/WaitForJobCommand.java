package com.github.ka4ok85.wca.command;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.JobOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

public class WaitForJobCommand extends AbstractCommand<JobResponse, JobOptions> {

	public WaitForJobCommand(OAuthClient oAuthClient) {
		super(oAuthClient);
	}

	@Override
	public ResponseContainer<JobResponse> executeCommand(JobOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		System.out.println("Running WaitForJobCommand with options " + options.getClass());

		JobResponse jobResponse = new JobResponse(0, "");
		ResponseContainer<JobResponse> response = new ResponseContainer<JobResponse>(jobResponse);

		System.out.println("End WaitForJobCommand");

		return response;

	}
}
