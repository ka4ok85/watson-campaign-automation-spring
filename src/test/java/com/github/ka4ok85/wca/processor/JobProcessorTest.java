package com.github.ka4ok85.wca.processor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.github.ka4ok85.wca.command.WaitForJobCommand;
import com.github.ka4ok85.wca.constants.JobStatus;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.JobOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.sftp.SFTP;

public class JobProcessorTest {
	@Test
	public void testWaitUntilJobIsCompleted() {
		OAuthClient oAuthClient = null;
		SFTP sftp = new SFTP(oAuthClient);
		Long jobId = 1L;
		JobOptions options = new JobOptions(jobId);

		WaitForJobCommand command = mock(WaitForJobCommand.class);

		JobResponse jobResponse = new JobResponse();
		ResponseContainer<JobResponse> responseContainer = new ResponseContainer<JobResponse>(jobResponse);

		when(command.executeCommand(options)).thenReturn(responseContainer);
		JobProcessor.waitUntilJobIsCompleted(options, oAuthClient, sftp, command);
	}

	@Test(expected = EngageApiException.class)
	public void testWaitUntilJobIsCompletedErrorResponse() {
		OAuthClient oAuthClient = null;
		SFTP sftp = new SFTP(oAuthClient);
		Long jobId = 1L;
		JobOptions options = new JobOptions(jobId);

		WaitForJobCommand command = mock(WaitForJobCommand.class);

		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobStatus(JobStatus.ERROR);
		ResponseContainer<JobResponse> responseContainer = new ResponseContainer<JobResponse>(jobResponse);

		when(command.executeCommand(options)).thenReturn(responseContainer);
		JobProcessor.waitUntilJobIsCompleted(options, oAuthClient, sftp, command);
	}
}
