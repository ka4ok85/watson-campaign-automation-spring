package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.github.ka4ok85.wca.response.containers.SentMailing;

@Component
@Scope("prototype")
public class GetSentMailingsForUserResponse extends AbstractResponse {
	private Long sentMailingsCount;
	private List<SentMailing> sentMailings = new ArrayList<SentMailing>();

	public Long getSentMailingsCount() {
		return sentMailingsCount;
	}

	public void setSentMailingsCount(Long sentMailingsCount) {
		this.sentMailingsCount = sentMailingsCount;
	}

	public List<SentMailing> getSentMailings() {
		return sentMailings;
	}

	public void setSentMailings(List<SentMailing> sentMailings) {
		this.sentMailings = sentMailings;
	}

	@Override
	public String toString() {
		return "GetSentMailingsForUserResponse [sentMailingsCount=" + sentMailingsCount + ", sentMailings="
				+ sentMailings + "]";
	}

}