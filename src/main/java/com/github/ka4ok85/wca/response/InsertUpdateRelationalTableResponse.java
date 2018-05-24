package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.response.containers.RelationalTableRecordFailure;

@Component
@Scope("prototype")
public class InsertUpdateRelationalTableResponse extends AbstractResponse {

	private List<RelationalTableRecordFailure> failures = new ArrayList<RelationalTableRecordFailure>();

	public List<RelationalTableRecordFailure> getFailures() {
		return failures;
	}

	public void setFailures(List<RelationalTableRecordFailure> failures) {
		this.failures = failures;
	}

	@Override
	public String toString() {
		return "InsertUpdateRelationalTableResponse [failures=" + failures + "]";
	}

}
