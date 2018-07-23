package com.github.ka4ok85.wca.command;

import java.util.Objects;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.options.ListRecipientMailingsOptions;
import com.github.ka4ok85.wca.response.ListRecipientMailingsResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA ListRecipientMailings API.</strong> It builds
 * XML request for ListRecipientMailings API using
 * {@link com.github.ka4ok85.wca.options.ListRecipientMailingsOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.ListRecipientMailingsResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class ListRecipientMailingsCommand  extends AbstractInstantCommand<ListRecipientMailingsResponse, ListRecipientMailingsOptions> {

	private static final String apiMethodName = "ListRecipientMailings";

	@Autowired
	private ListRecipientMailingsResponse listRecipientMailingsResponse;

	/**
	 * Builds XML request for ListRecipientMailings API using
	 * {@link com.github.ka4ok85.wca.options.ListRecipientMailingsOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(ListRecipientMailingsOptions options) {

	}

	/**
	 * Reads ListRecipientMailings API response into
	 * {@link com.github.ka4ok85.wca.response.ListRecipientMailingsResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO ListRecipientMailingsResponse
	 */
	@Override
	public ResponseContainer<ListRecipientMailingsResponse> readResponse(Node resultNode, ListRecipientMailingsOptions options) {
		return null;
	}

}
