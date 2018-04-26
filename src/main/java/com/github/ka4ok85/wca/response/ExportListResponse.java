package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.constants.FileEncoding;

@Component
@Scope("prototype")
public class ExportListResponse extends AbstractResponse {

	private String remoteFileName;
	private String description;
	private FileEncoding fileEncoding;
	private boolean keepInFtpDownloadDirectory;
	private boolean keepInStoredFiles;
	private String listName;

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FileEncoding getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(FileEncoding fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public boolean isKeepInFtpDownloadDirectory() {
		return keepInFtpDownloadDirectory;
	}

	public void setKeepInFtpDownloadDirectory(boolean keepInFtpDownloadDirectory) {
		this.keepInFtpDownloadDirectory = keepInFtpDownloadDirectory;
	}

	public boolean isKeepInStoredFiles() {
		return keepInStoredFiles;
	}

	public void setKeepInStoredFiles(boolean keepInStoredFiles) {
		this.keepInStoredFiles = keepInStoredFiles;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	@Override
	public String toString() {
		return "ExportListResponse [remoteFileName=" + remoteFileName + ", description=" + description
				+ ", fileEncoding=" + fileEncoding + ", keepInFtpDownloadDirectory=" + keepInFtpDownloadDirectory
				+ ", keepInStoredFiles=" + keepInStoredFiles + ", listName=" + listName + "]";
	}

}
