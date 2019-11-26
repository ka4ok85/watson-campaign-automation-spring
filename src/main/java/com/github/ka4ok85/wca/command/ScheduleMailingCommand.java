package com.github.ka4ok85.wca.command;

import com.github.ka4ok85.wca.options.ScheduleMailingOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.ScheduleMailingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Objects;

/*
 * TODO: ScheduleMailing API has additional parameters that are not yet added to this class
 */
/**
 * <strong>Class for interacting with WCA ScheduleMailing API.</strong> It builds XML
 * request for ScheduleMailing API using
 * {@link com.github.ka4ok85.wca.options.ScheduleMailingOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.ScheduleMailingResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Gissel Serrala
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class ScheduleMailingCommand extends AbstractInstantCommand<ScheduleMailingResponse, ScheduleMailingOptions> {

    private static final String apiMethodName = "ScheduleMailing";

    @Autowired
    private ScheduleMailingResponse scheduleMailingResponse;

    /**
     * Builds XML request for ScheduleMailing API using
     * {@link ScheduleMailingOptions}
     *
     * @param options
     *            - settings for API call
     */
    @Override
    public void buildXmlRequest(ScheduleMailingOptions options) {
        Objects.requireNonNull(options, "ScheduleMailingResponse must not be null");

        Element methodElement = doc.createElement(apiMethodName);
        currentNode = addChildNode(methodElement, null);

        Element templateId = doc.createElement("TEMPLATE_ID");
        templateId.setTextContent(options.getTemplateId().toString());
        addChildNode(templateId, currentNode);

        Element listId = doc.createElement("LIST_ID");
        listId.setTextContent(options.getListId().toString());
        addChildNode(listId, currentNode);

        Element mailingName = doc.createElement("MAILING_NAME");
        mailingName.setTextContent(options.getMailingName());
        addChildNode(mailingName, currentNode);

        if (options.isSendText()) {
            Element email = doc.createElement("SEND_TEXT");
            addChildNode(email, currentNode);
        }

        Element visibility = doc.createElement("VISIBILITY");
        visibility.setTextContent(options.getVisibility().value().toString());
        addChildNode(visibility, currentNode);

        Element scheduled = doc.createElement("SCHEDULED");
        scheduled.setTextContent(options.getScheduled());
        addChildNode(scheduled, currentNode);
    }

    /**
     * Reads RemoveRecipient API response into
     * {@link com.github.ka4ok85.wca.response.RemoveRecipientResponse}
     *
     * @param resultNode
     *            - "RESULT" XML Node returned by API
     * @param options
     *            - settings for API call
     * @return POJO RemoveRecipient Response
     */
    @Override
    public ResponseContainer<ScheduleMailingResponse> readResponse(Node resultNode, ScheduleMailingOptions options) {
        ResponseContainer<ScheduleMailingResponse> response = new ResponseContainer<ScheduleMailingResponse>(
                scheduleMailingResponse);

        return response;
    }
}
