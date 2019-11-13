package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ScheduleMailingResponse extends AbstractResponse {
    private Long mailingId;

    public ScheduleMailingResponse() {
    }

    public Long getMailingId() {
        return this.mailingId;
    }

    public void setMailingId(Long tableId) {
        this.mailingId = tableId;
    }

    public String toString() {
        return "ScheduleMailingResponse [mailingId=" + this.mailingId + "]";
    }
}
