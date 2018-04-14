package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import com.github.ka4ok85.wca.response.containers.InsertUpdateRelationalTableFailure;

public class InsertUpdateRelationalTableResponse extends AbstractResponse {

	private List<InsertUpdateRelationalTableFailure> failures = new ArrayList<InsertUpdateRelationalTableFailure>();

	public List<InsertUpdateRelationalTableFailure> getFailures() {
		return failures;
	}

	public void setFailures(List<InsertUpdateRelationalTableFailure> failures) {
		this.failures = failures;
	}

	@Override
	public String toString() {
		return "InsertUpdateRelationalTableResponse [failures=" + failures + "]";
	}

}
