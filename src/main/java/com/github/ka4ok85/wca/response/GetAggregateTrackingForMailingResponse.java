package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataClicks;
import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataInboxMonitoring;
import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataMailing;
import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataTopDomain;

@Component
@Scope("prototype")
public class GetAggregateTrackingForMailingResponse extends AbstractResponse {

	private List<AggregateTrackingDataClicks> clicks = new ArrayList<AggregateTrackingDataClicks>();
	private List<AggregateTrackingDataInboxMonitoring> inboxMonitorings = new ArrayList<AggregateTrackingDataInboxMonitoring>();
	private List<AggregateTrackingDataMailing> mailings = new ArrayList<AggregateTrackingDataMailing>();
	private List<AggregateTrackingDataTopDomain> topDomains = new ArrayList<AggregateTrackingDataTopDomain>();

	public List<AggregateTrackingDataClicks> getClicks() {
		return clicks;
	}

	public void setClicks(List<AggregateTrackingDataClicks> clicks) {
		this.clicks = clicks;
	}

	public List<AggregateTrackingDataInboxMonitoring> getInboxMonitorings() {
		return inboxMonitorings;
	}

	public void setInboxMonitorings(List<AggregateTrackingDataInboxMonitoring> inboxMonitorings) {
		this.inboxMonitorings = inboxMonitorings;
	}

	public List<AggregateTrackingDataMailing> getMailings() {
		return mailings;
	}

	public void setMailings(List<AggregateTrackingDataMailing> mailings) {
		this.mailings = mailings;
	}

	public List<AggregateTrackingDataTopDomain> getTopDomains() {
		return topDomains;
	}

	public void setTopDomains(List<AggregateTrackingDataTopDomain> topDomains) {
		this.topDomains = topDomains;
	}

	@Override
	public String toString() {
		return "GetAggregateTrackingForMailingResponse [clicks=" + clicks + ", inboxMonitorings=" + inboxMonitorings
				+ ", mailings=" + mailings + ", topDomains=" + topDomains + "]";
	}

}