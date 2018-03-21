package com.github.ka4ok85.wca.options;

import java.util.List;

import com.github.ka4ok85.wca.constants.ExportFormat;
import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.constants.ListExportType;
import com.github.ka4ok85.wca.utils.DateTimeRange;

public class ExportListOptions extends AbstractOptions {

	private ListExportType exportType = ListExportType.ALL;
	private ExportFormat exportFormat = ExportFormat.CSV;
	private FileEncoding fileEncoding = FileEncoding.UTF_8;
	private boolean includeRecipientId = false;
	private boolean includeListId = false;
	private boolean includeLeadSource = false;
	private boolean addToStoredFiles = false;
	private DateTimeRange lastModifiedRange;
	private List<String> exportColumns;
	private final Long listId;
	private String localAbsoluteFilePath;

	public ExportListOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		this.listId = listId;
	}

	public Long getListId() {
		return listId;
	}

	public ListExportType getExportType() {
		return exportType;
	}

	public void setExportType(ListExportType exportType) {
		this.exportType = exportType;
	}

	public ExportFormat getExportFormat() {
		return exportFormat;
	}

	public void setExportFormat(ExportFormat exportFormat) {
		this.exportFormat = exportFormat;
	}

	public FileEncoding getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(FileEncoding fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public boolean isIncludeRecipientId() {
		return includeRecipientId;
	}

	public void setIncludeRecipientId(boolean includeRecipientId) {
		this.includeRecipientId = includeRecipientId;
	}

	public boolean isIncludeListId() {
		return includeListId;
	}

	public void setIncludeListId(boolean includeListId) {
		this.includeListId = includeListId;
	}

	public boolean isIncludeLeadSource() {
		return includeLeadSource;
	}

	public void setIncludeLeadSource(boolean includeLeadSource) {
		this.includeLeadSource = includeLeadSource;
	}

	public boolean isAddToStoredFiles() {
		return addToStoredFiles;
	}

	public void setAddToStoredFiles(boolean addToStoredFiles) {
		this.addToStoredFiles = addToStoredFiles;
	}

	public DateTimeRange getLastModifiedRange() {
		return lastModifiedRange;
	}

	public void setLastModifiedRange(DateTimeRange lastModifiedRange) {
		this.lastModifiedRange = lastModifiedRange;
	}

	public List<String> getExportColumns() {
		return exportColumns;
	}

	public void setExportColumns(List<String> exportColumns) {
		this.exportColumns = exportColumns;
	}

	public String getLocalAbsoluteFilePath() {
		return localAbsoluteFilePath;
	}

	public void setLocalAbsoluteFilePath(String localAbsoluteFilePath) {
		this.localAbsoluteFilePath = localAbsoluteFilePath;
	}

	@Override
	public String toString() {
		return "ExportListOptions [exportType=" + exportType + ", exportFormat=" + exportFormat + ", fileEncoding="
				+ fileEncoding + ", includeRecipientId=" + includeRecipientId + ", includeListId=" + includeListId
				+ ", includeLeadSource=" + includeLeadSource + ", addToStoredFiles=" + addToStoredFiles
				+ ", lastModifiedRange=" + lastModifiedRange + ", exportColumns=" + exportColumns + ", listId=" + listId
				+ ", localAbsoluteFilePath=" + localAbsoluteFilePath + "]";
	}

}
