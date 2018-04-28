package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.response.containers.ReportIdByDateMailing;

@Component
@Scope("prototype")
public class GetReportIdByDateResponse extends AbstractResponse {

	private List<ReportIdByDateMailing> mailings = new ArrayList<ReportIdByDateMailing>();

	public List<ReportIdByDateMailing> getMailings() {
		return mailings;
	}

	public void setMailings(List<ReportIdByDateMailing> mailings) {
		this.mailings = mailings;
	}

	@Override
	public String toString() {
		return "GetReportIdByDateResponse [mailings=" + mailings + "]";
	}

}
