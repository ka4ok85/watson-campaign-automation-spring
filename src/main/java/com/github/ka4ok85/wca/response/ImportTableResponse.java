package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ImportTableResponse extends AbstractResponse {
	private Long jobId;
	private String description;
	private Long badRecordsNumber;
	private Long totalValidNumber;
	private Long notAllowedNumber;
	private Long badAddressesNumber;
	private Long totalRowsNumber;
	private Long duplicatesNumber;
	private Long sqlAddedNumber;
	private Long sqlUpdatedNumber;
	private String errorFileName;
	private String resultsFileName;
	private String listName;
	private Long listId;
	private String parentFolderName;
	private boolean smsConsent;
	private String syncFieldsFromMappingFile;
	private String listProgramLists;
	private Long calculateListJobId;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getBadRecordsNumber() {
		return badRecordsNumber;
	}

	public void setBadRecordsNumber(Long badRecordsNumber) {
		this.badRecordsNumber = badRecordsNumber;
	}

	public Long getTotalValidNumber() {
		return totalValidNumber;
	}

	public void setTotalValidNumber(Long totalValidNumber) {
		this.totalValidNumber = totalValidNumber;
	}

	public Long getNotAllowedNumber() {
		return notAllowedNumber;
	}

	public void setNotAllowedNumber(Long notAllowedNumber) {
		this.notAllowedNumber = notAllowedNumber;
	}

	public Long getBadAddressesNumber() {
		return badAddressesNumber;
	}

	public void setBadAddressesNumber(Long badAddressesNumber) {
		this.badAddressesNumber = badAddressesNumber;
	}

	public Long getTotalRowsNumber() {
		return totalRowsNumber;
	}

	public void setTotalRowsNumber(Long totalRowsNumber) {
		this.totalRowsNumber = totalRowsNumber;
	}

	public Long getDuplicatesNumber() {
		return duplicatesNumber;
	}

	public void setDuplicatesNumber(Long duplicatesNumber) {
		this.duplicatesNumber = duplicatesNumber;
	}

	public Long getSqlAddedNumber() {
		return sqlAddedNumber;
	}

	public void setSqlAddedNumber(Long sqlAddedNumber) {
		this.sqlAddedNumber = sqlAddedNumber;
	}

	public Long getSqlUpdatedNumber() {
		return sqlUpdatedNumber;
	}

	public void setSqlUpdatedNumber(Long sqlUpdatedNumber) {
		this.sqlUpdatedNumber = sqlUpdatedNumber;
	}

	public String getErrorFileName() {
		return errorFileName;
	}

	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}

	public String getResultsFileName() {
		return resultsFileName;
	}

	public void setResultsFileName(String resultsFileName) {
		this.resultsFileName = resultsFileName;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public String getParentFolderName() {
		return parentFolderName;
	}

	public void setParentFolderName(String parentFolderName) {
		this.parentFolderName = parentFolderName;
	}

	public boolean isSmsConsent() {
		return smsConsent;
	}

	public void setSmsConsent(boolean smsConsent) {
		this.smsConsent = smsConsent;
	}

	public String getSyncFieldsFromMappingFile() {
		return syncFieldsFromMappingFile;
	}

	public void setSyncFieldsFromMappingFile(String syncFieldsFromMappingFile) {
		this.syncFieldsFromMappingFile = syncFieldsFromMappingFile;
	}

	public String getListProgramLists() {
		return listProgramLists;
	}

	public void setListProgramLists(String listProgramLists) {
		this.listProgramLists = listProgramLists;
	}

	public Long getCalculateListJobId() {
		return calculateListJobId;
	}

	public void setCalculateListJobId(Long calculateListJobId) {
		this.calculateListJobId = calculateListJobId;
	}

	@Override
	public String toString() {
		return "ImportTableResponse [jobId=" + jobId + ", description=" + description + ", badRecordsNumber="
				+ badRecordsNumber + ", totalValidNumber=" + totalValidNumber + ", notAllowedNumber=" + notAllowedNumber
				+ ", badAddressesNumber=" + badAddressesNumber + ", totalRowsNumber=" + totalRowsNumber
				+ ", duplicatesNumber=" + duplicatesNumber + ", sqlAddedNumber=" + sqlAddedNumber
				+ ", sqlUpdatedNumber=" + sqlUpdatedNumber + ", errorFileName=" + errorFileName + ", resultsFileName="
				+ resultsFileName + ", listName=" + listName + ", listId=" + listId + ", parentFolderName="
				+ parentFolderName + ", smsConsent=" + smsConsent + ", syncFieldsFromMappingFile="
				+ syncFieldsFromMappingFile + ", listProgramLists=" + listProgramLists + ", calculateListJobId="
				+ calculateListJobId + "]";
	}

}
