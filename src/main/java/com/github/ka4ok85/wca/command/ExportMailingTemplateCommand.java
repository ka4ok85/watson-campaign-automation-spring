package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.ExportMailingTemplateOptions;
import com.github.ka4ok85.wca.response.ExportMailingTemplateResponse;

@Service
@Scope("prototype")
public class ExportMailingTemplateCommand extends AbstractCommand<ExportMailingTemplateResponse, ExportMailingTemplateOptions> {

	private static final String apiMethodName = "ExportMailingTemplate";
	private static final Logger log = LoggerFactory.getLogger(ExportMailingTemplateCommand.class);

	@Autowired
	private ExportMailingTemplateResponse exportMailingTemplateResponse;
}
