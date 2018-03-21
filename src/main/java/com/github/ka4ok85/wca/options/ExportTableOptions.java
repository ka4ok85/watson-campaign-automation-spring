package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.ExportFormat;
import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.utils.DateTimeRange;

public class ExportTableOptions extends AbstractOptions {
	private ExportFormat exportFormat = ExportFormat.CSV;
	private FileEncoding fileEncoding = FileEncoding.UTF_8;
	private boolean addToStoredFiles = false;
	private DateTimeRange lastModifiedRange;
	private final Long tableId;
	private String localAbsoluteFilePath;

	public ExportTableOptions(Long tableId) {
		super();
		if (tableId < 1) {
			throw new RuntimeException("Table ID must be greater than zero. Provided Table ID = " + tableId);
		}

		this.tableId = tableId;
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

	public String getLocalAbsoluteFilePath() {
		return localAbsoluteFilePath;
	}

	public void setLocalAbsoluteFilePath(String localAbsoluteFilePath) {
		this.localAbsoluteFilePath = localAbsoluteFilePath;
	}

	public Long getTableId() {
		return tableId;
	}

	@Override
	public String toString() {
		return "ExportTableOptions [exportFormat=" + exportFormat + ", fileEncoding=" + fileEncoding
				+ ", addToStoredFiles=" + addToStoredFiles + ", lastModifiedRange=" + lastModifiedRange + ", tableId="
				+ tableId + ", localAbsoluteFilePath=" + localAbsoluteFilePath + "]";
	}

}
