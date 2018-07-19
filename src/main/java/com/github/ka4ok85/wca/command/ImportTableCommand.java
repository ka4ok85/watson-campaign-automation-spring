package com.github.ka4ok85.wca.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.ImportTableOptions;
import com.github.ka4ok85.wca.response.ImportTableResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA ImportTable API.</strong> It builds
 * XML request for ImportTable API using
 * {@link com.github.ka4ok85.wca.options.ImportTableOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.ImportTableResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * It relies on parent {@link com.github.ka4ok85.wca.command.AbstractJobCommand}
 * for polling mechanism.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class ImportTableCommand extends AbstractJobCommand<ImportTableResponse, ImportTableOptions> {

	private static final String apiMethodName = "ImportTable";

	@Autowired
	private ImportTableResponse importTableResponse;

	@Override
	public ResponseContainer<ImportTableResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, ImportTableOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildXmlRequest(ImportTableOptions options) {
		// TODO Auto-generated method stub

	}

}