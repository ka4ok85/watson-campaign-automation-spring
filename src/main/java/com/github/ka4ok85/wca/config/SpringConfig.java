package com.github.ka4ok85.wca.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.ka4ok85.wca.Engage;
import com.github.ka4ok85.wca.command.GetListMetaDataCommand;
import com.github.ka4ok85.wca.command.GetListsCommand;
import com.github.ka4ok85.wca.command.GetMailingTemplatesCommand;
import com.github.ka4ok85.wca.command.InsertUpdateRelationalTableCommand;
import com.github.ka4ok85.wca.command.JoinTableCommand;
import com.github.ka4ok85.wca.command.OptOutRecipientCommand;
import com.github.ka4ok85.wca.command.PurgeTableCommand;
import com.github.ka4ok85.wca.command.RemoveRecipientCommand;
import com.github.ka4ok85.wca.command.SelectRecipientDataCommand;
import com.github.ka4ok85.wca.command.UpdateRecipientCommand;
import com.github.ka4ok85.wca.command.WaitForJobCommand;

@Configuration
@ComponentScan("com.github.ka4ok85.wca")
public class SpringConfig {

	@Bean
	@Scope("prototype")
	public Engage engage(@Value("${podNumber}") int podNumber, @Value("${clientId}") String clientId,
			@Value("${clientSecret}") String clientSecret, @Value("${refreshToken}") String refreshToken) {
		return new Engage(podNumber, clientId, clientSecret, refreshToken);
	}

	@Bean
	@Scope("prototype")
	public SelectRecipientDataCommand selectRecipientData() {
		return new SelectRecipientDataCommand();
	}

	@Bean
	@Scope("prototype")
	public UpdateRecipientCommand updateRecipient() {
		return new UpdateRecipientCommand();
	}

	@Bean
	@Scope("prototype")
	public OptOutRecipientCommand optOutRecipient() {
		return new OptOutRecipientCommand();
	}

	@Bean
	@Scope("prototype")
	public RemoveRecipientCommand removeRecipient() {
		return new RemoveRecipientCommand();
	}

	@Bean
	@Scope("prototype")
	public GetListsCommand getLists() {
		return new GetListsCommand();
	}

	@Bean
	@Scope("prototype")
	public JoinTableCommand joinTable() {
		return new JoinTableCommand();
	}

	@Bean
	@Scope("prototype")
	public InsertUpdateRelationalTableCommand insertUpdateRelationalTable() {
		return new InsertUpdateRelationalTableCommand();
	}

	@Bean
	@Scope("prototype")
	public PurgeTableCommand purgeTable() {
		return new PurgeTableCommand();
	}

	@Bean
	@Scope("prototype")
	public WaitForJobCommand waitForJobCommand() {
		return new WaitForJobCommand();
	}

	@Bean
	@Scope("prototype")
	public GetListMetaDataCommand getListMetaData() {
		return new GetListMetaDataCommand();
	}

	@Bean
	@Scope("prototype")
	public GetMailingTemplatesCommand getMailingTemplates() {
		return new GetMailingTemplatesCommand();
	}
}
