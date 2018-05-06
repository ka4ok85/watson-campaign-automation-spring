package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.response.containers.TrackingMetricExportMailing;

@Component
@Scope("prototype")
public class TrackingMetricExportResponse extends AbstractResponse {

	private List<TrackingMetricExportMailing> mailings = new ArrayList<TrackingMetricExportMailing>();

	public List<TrackingMetricExportMailing> getMailings() {
		return mailings;
	}

	public void setMailings(List<TrackingMetricExportMailing> mailings) {
		this.mailings = mailings;
	}

	@Override
	public String toString() {
		return "TrackingMetricExportResponse []";
	}

}