package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ExportListResponse extends AbstractResponse {

	private String remoteFileName;

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	@Override
	public String toString() {
		return "ExportListResponse [remoteFileName=" + remoteFileName + "]";
	}

}
