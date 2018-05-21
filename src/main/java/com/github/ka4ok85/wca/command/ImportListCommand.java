package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.ImportListOptions;
import com.github.ka4ok85.wca.response.ImportListResponse;

@Service
@Scope("prototype")
public class ImportListCommand extends AbstractCommand<ImportListResponse, ImportListOptions> {

	private static final String apiMethodName = "ImportList";
	private static final Logger log = LoggerFactory.getLogger(ImportListCommand.class);

	@Autowired
	private ImportListResponse importListResponse;
}
