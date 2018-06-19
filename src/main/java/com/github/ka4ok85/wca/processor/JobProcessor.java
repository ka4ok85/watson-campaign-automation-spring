package com.github.ka4ok85.wca.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.command.WaitForJobCommand;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.JobOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.sftp.SFTP;

public class JobProcessor {

	private static final int maxExecutionTime = 86400;
	private static final int jobCheckInterval = 10;

	private static final Logger log = LoggerFactory.getLogger(JobProcessor.class);

	public static JobResponse waitUntilJobIsCompleted(Long jobId, OAuthClient oAuthClient, SFTP sftp) {
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
				throw new EngageApiException("WaitForJobCommand failure: " + response.getJobDescription());
			}

			if (response.isCanceled()) {
				throw new JobBadStateException("Job was canceled!");
			}

			if (response.isRunning() || response.isWaiting()) {
				try {
					Thread.sleep(jobCheckInterval * 1000);
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
