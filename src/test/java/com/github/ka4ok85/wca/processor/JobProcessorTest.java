package com.github.ka4ok85.wca.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.github.ka4ok85.wca.command.WaitForJobCommand;
import com.github.ka4ok85.wca.constants.JobStatus;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
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
		JobResponse response = JobProcessor.waitUntilJobIsCompleted(options, oAuthClient, sftp, command, false);
		assertEquals(response, jobResponse);
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
		JobProcessor.waitUntilJobIsCompleted(options, oAuthClient, sftp, command, false);
	}

	@Test(expected = JobBadStateException.class)
	public void testWaitUntilJobIsCompletedCancelledResponse() {
		OAuthClient oAuthClient = null;
		SFTP sftp = new SFTP(oAuthClient);
		Long jobId = 1L;
		JobOptions options = new JobOptions(jobId);

		WaitForJobCommand command = mock(WaitForJobCommand.class);

		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobStatus(JobStatus.CANCELED);
		ResponseContainer<JobResponse> responseContainer = new ResponseContainer<JobResponse>(jobResponse);

		when(command.executeCommand(options)).thenReturn(responseContainer);
		JobProcessor.waitUntilJobIsCompleted(options, oAuthClient, sftp, command, false);
	}

	@Test
	public void testWaitUntilJobIsCompletedRunning() {
		OAuthClient oAuthClient = null;
		SFTP sftp = new SFTP(oAuthClient);
		Long jobId = 1L;
		JobOptions options = new JobOptions(jobId);

		WaitForJobCommand command = mock(WaitForJobCommand.class);

		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobStatus(JobStatus.RUNNING);
		ResponseContainer<JobResponse> responseContainer = new ResponseContainer<JobResponse>(jobResponse);

		when(command.executeCommand(options)).thenReturn(responseContainer);
		JobProcessor.setJobCheckInterval(1);
		JobProcessor.setMaxExecutionTime(5);
		JobResponse response = JobProcessor.waitUntilJobIsCompleted(options, oAuthClient, sftp, command, false);
		assertNull(response);
	}

	@Test
	public void testWaitUntilJobIsCompletedWaiting() {
		OAuthClient oAuthClient = null;
		SFTP sftp = new SFTP(oAuthClient);
		Long jobId = 1L;
		JobOptions options = new JobOptions(jobId);

		WaitForJobCommand command = mock(WaitForJobCommand.class);

		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobStatus(JobStatus.WAITING);
		ResponseContainer<JobResponse> responseContainer = new ResponseContainer<JobResponse>(jobResponse);

		when(command.executeCommand(options)).thenReturn(responseContainer);
		JobProcessor.setJobCheckInterval(1);
		JobProcessor.setMaxExecutionTime(5);
		JobResponse response = JobProcessor.waitUntilJobIsCompleted(options, oAuthClient, sftp, command, false);
		assertNull(response);
	}
}
