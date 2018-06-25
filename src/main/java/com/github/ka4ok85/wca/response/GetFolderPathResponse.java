package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.constants.GetFolderPathObjectSubType;

@Component
@Scope("prototype")
public class GetFolderPathResponse extends AbstractResponse {
	private String folderPath;
	private GetFolderPathObjectSubType objectSubType;

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public GetFolderPathObjectSubType getObjectSubType() {
		return objectSubType;
	}

	public void setObjectSubType(GetFolderPathObjectSubType objectSubType) {
		this.objectSubType = objectSubType;
	}

	@Override
	public String toString() {
		return "GetFolderPathResponse [folderPath=" + folderPath + ", objectSubType=" + objectSubType + "]";
	}

}
