package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.Visibility;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleMailingOptions extends AbstractOptions {
    private Visibility visibility;
    private String scheduled;
    private Long templateId;
    private Long listId;
    private String mailingName;
    private boolean sendText;
    private int scheduleInMinutes = 2;

    public ScheduleMailingOptions(Long templateId, Long listId, String mailingName){
        this.templateId = templateId;
        this.listId = listId;
        this.mailingName = mailingName;
        this.visibility = Visibility.SHARED;
        this.scheduled = getCurrentTimeStampPlusMinutes();
        this.sendText = true;
    }

    public String getCurrentTimeStampPlusMinutes() {
        Date targetTime = new Date(); //now
        targetTime = DateUtils.addMinutes(targetTime, scheduleInMinutes); //add minute
        return new SimpleDateFormat("dd/mm/yyyy hh:mm:ss a").format(targetTime);
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        if (templateId < 1L) {
            throw new RuntimeException("Template ID must be greater than zero. Provided Template ID = " + templateId);
        } else {
            this.templateId = templateId;
        }
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        if (listId < 1L) {
            throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
        } else {
            this.listId = listId;
        }
    }

    public String getMailingName() {
        return mailingName;
    }

    public void setMailingName(String mailingName) {
        this.mailingName = mailingName;
    }

    public boolean isSendText() {
        return sendText;
    }

    public void setSendText(boolean sendText) {
        this.sendText = sendText;
    }

    public int getScheduleInMinutes() {
        return scheduleInMinutes;
    }

    public void setScheduleInMinutes(int scheduleInMinutes) {
        this.scheduleInMinutes = scheduleInMinutes;
        this.scheduled = getCurrentTimeStampPlusMinutes();
    }
}
