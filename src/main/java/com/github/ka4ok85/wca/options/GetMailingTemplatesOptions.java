package com.github.ka4ok85.wca.options;

import java.time.LocalDateTime;

import com.github.ka4ok85.wca.constants.Visibility;

public class GetMailingTemplatesOptions extends AbstractOptions {

	private Visibility visibility = Visibility.SHARED;
	private boolean isCrmEnabled = false;
	private LocalDateTime lastModifiedStartDate;
	private LocalDateTime lastModifiedEndDate;

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isCrmEnabled() {
		return isCrmEnabled;
	}

	public void setCrmEnabled(boolean isCrmEnabled) {
		this.isCrmEnabled = isCrmEnabled;
	}

	public LocalDateTime getLastModifiedStartDate() {
		return lastModifiedStartDate;
	}

	public void setLastModifiedStartDate(LocalDateTime lastModifiedStartDate) {
		this.lastModifiedStartDate = lastModifiedStartDate;
	}

	public LocalDateTime getLastModifiedEndDate() {
		return lastModifiedEndDate;
	}

	public void setLastModifiedEndDate(LocalDateTime lastModifiedEndDate) {
		this.lastModifiedEndDate = lastModifiedEndDate;
	}

	@Override
	public String toString() {
		return "GetMailingTemplatesOptions [visibility=" + visibility + ", isCrmEnabled=" + isCrmEnabled
				+ ", lastModifiedStartDate=" + lastModifiedStartDate + ", lastModifiedEndDate=" + lastModifiedEndDate
				+ "]";
	}

}
