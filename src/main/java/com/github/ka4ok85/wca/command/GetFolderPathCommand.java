package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.GetFolderPathOptions;
import com.github.ka4ok85.wca.response.GetFolderPathResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@Service
@Scope("prototype")
public class GetFolderPathCommand extends AbstractJobCommand<GetFolderPathResponse, GetFolderPathOptions> {

	private static final String apiMethodName = "GetFolderPath";
	private static final Logger log = LoggerFactory.getLogger(GetFolderPathCommand.class);

	@Autowired
	private GetFolderPathResponse getFolderPathResponse;

	@Override
	public ResponseContainer<GetFolderPathResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, GetFolderPathOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildXmlRequest(GetFolderPathOptions options) {
		// TODO Auto-generated method stub

	}

}
