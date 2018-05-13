package com.github.ka4ok85.wca.options;

public class ExportMailingTemplateOptions extends AbstractOptions {

	private final Long templateId;
	private boolean addToStoredFiles;
	private String localAbsoluteFilePath;

	public ExportMailingTemplateOptions(Long templateId) {
		super();
		if (templateId == null || templateId < 1) {
			throw new RuntimeException("Template ID must be greater than zero. Provided Template ID = " + templateId);
		}

		this.templateId = templateId;
	}

	public boolean isAddToStoredFiles() {
		return addToStoredFiles;
	}

	public void setAddToStoredFiles(boolean addToStoredFiles) {
		this.addToStoredFiles = addToStoredFiles;
	}

	public String getLocalAbsoluteFilePath() {
		return localAbsoluteFilePath;
	}

	public void setLocalAbsoluteFilePath(String localAbsoluteFilePath) {
		this.localAbsoluteFilePath = localAbsoluteFilePath;
	}

	public Long getTemplateId() {
		return templateId;
	}

	@Override
	public String toString() {
		return "ExportMailingTemplateOptions [templateId=" + templateId + ", addToStoredFiles=" + addToStoredFiles
				+ ", localAbsoluteFilePath=" + localAbsoluteFilePath + "]";
	}

}