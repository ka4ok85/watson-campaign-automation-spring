package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.sftp.SFTP;

public class ExportTableCommand extends AbstractCommand<ExportTableResponse, ExportTableOptions> {

	private static final String apiMethodName = "ExportTable";
	private static final Logger log = LoggerFactory.getLogger(ExportTableCommand.class);

	public ExportTableCommand(OAuthClient oAuthClient, SFTP sftp) {
		super(oAuthClient, sftp);
	}

}
