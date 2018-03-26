package com.github.ka4ok85.wca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.ka4ok85.wca.command.CreateContactListCommand;
import com.github.ka4ok85.wca.command.DeleteListCommand;
import com.github.ka4ok85.wca.command.ExportListCommand;
import com.github.ka4ok85.wca.command.ExportTableCommand;
import com.github.ka4ok85.wca.command.WaitForJobCommand;

@Configuration
public class SpringConfig {
	@Bean
	@Scope("prototype")
	public ExportListCommand exportList() {
		return new ExportListCommand();
	}

	@Bean
	@Scope("prototype")
	public ExportTableCommand exportTable() {
		return new ExportTableCommand();
	}

	@Bean
	@Scope("prototype")
	public CreateContactListCommand createContactList() {
		return new CreateContactListCommand();
	}

	@Bean
	@Scope("prototype")
	public DeleteListCommand deleteList() {
		return new DeleteListCommand();
	}

	@Bean
	@Scope("prototype")
	public WaitForJobCommand waitForJobCommand() {
		return new WaitForJobCommand();
	}

}
