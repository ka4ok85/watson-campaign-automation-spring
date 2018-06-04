package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.PurgeDataOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.PurgeDataResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@Service
@Scope("prototype")
public class PurgeDataCommand extends AbstractJobCommand<PurgeDataResponse, PurgeDataOptions> {

	private static final String apiMethodName = "PurgeData";

	@Autowired
	private PurgeDataResponse purgeDataResponse;

	@Override
	public void buildXmlRequest(PurgeDataOptions options) {
		Objects.requireNonNull(options, "PurgeDataOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element targetId = doc.createElement("TARGET_ID");
		targetId.setTextContent(options.getTargetId().toString());
		addChildNode(targetId, currentNode);

		Element sourceId = doc.createElement("SOURCE_ID");
		sourceId.setTextContent(options.getSourceId().toString());
		addChildNode(sourceId, currentNode);
	}

	@Override
	public ResponseContainer<PurgeDataResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, PurgeDataOptions options) {
		Long jobId = jobResponse.getJobId();
		String description = jobResponse.getJobDescription();
		Long purgedContactListId = Long.parseLong(jobResponse.getParameters().get("LIST_ID_C"));
		String purgedContactListName = jobResponse.getParameters().get("LIST_NAME_C");
		Long purgedContactListSize = Long.parseLong(jobResponse.getParameters().get("LIST_SIZE"));
		String sourceListName = jobResponse.getParameters().get("LIST2_NAME");
		String targetListName = jobResponse.getParameters().get("LIST1_NAME");

		purgeDataResponse.setJobId(jobId);
		purgeDataResponse.setDescription(description);
		purgeDataResponse.setPurgedContactListId(purgedContactListId);
		purgeDataResponse.setPurgedContactListName(purgedContactListName);
		purgeDataResponse.setPurgedContactListSize(purgedContactListSize);
		purgeDataResponse.setSourceListName(sourceListName);
		purgeDataResponse.setTargetListName(targetListName);

		ResponseContainer<PurgeDataResponse> response = new ResponseContainer<PurgeDataResponse>(purgeDataResponse);

		return response;
	}

}
