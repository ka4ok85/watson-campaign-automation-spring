package com.github.ka4ok85.wca.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.github.ka4ok85.wca.CommandFactory;
import com.github.ka4ok85.wca.Engage;
import com.github.ka4ok85.wca.command.AddRecipientCommand;
import com.github.ka4ok85.wca.command.CalculateQueryCommand;
import com.github.ka4ok85.wca.command.CreateContactListCommand;
import com.github.ka4ok85.wca.command.CreateTableCommand;
import com.github.ka4ok85.wca.command.DeleteListCommand;
import com.github.ka4ok85.wca.command.DeleteRelationalTableDataCommand;
import com.github.ka4ok85.wca.command.DeleteTableCommand;
import com.github.ka4ok85.wca.command.DoubleOptInRecipientCommand;
import com.github.ka4ok85.wca.command.ExportListCommand;
import com.github.ka4ok85.wca.command.ExportTableCommand;
import com.github.ka4ok85.wca.command.GetListMetaDataCommand;
import com.github.ka4ok85.wca.command.GetListsCommand;
import com.github.ka4ok85.wca.command.InsertUpdateRelationalTableCommand;
import com.github.ka4ok85.wca.command.JoinTableCommand;
import com.github.ka4ok85.wca.command.OptOutRecipientCommand;
import com.github.ka4ok85.wca.command.PurgeTableCommand;
import com.github.ka4ok85.wca.command.RemoveRecipientCommand;
import com.github.ka4ok85.wca.command.SelectRecipientDataCommand;
import com.github.ka4ok85.wca.command.UpdateRecipientCommand;
import com.github.ka4ok85.wca.command.WaitForJobCommand;
import com.github.ka4ok85.wca.response.ExportListResponse;

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
	public SelectRecipientDataCommand selectRecipientData() {
		return new SelectRecipientDataCommand();
	}

	@Bean
	@Scope("prototype")
	public AddRecipientCommand addRecipient() {
		return new AddRecipientCommand();
	}

	@Bean
	@Scope("prototype")
	public DoubleOptInRecipientCommand doubleOptInRecipient() {
		return new DoubleOptInRecipientCommand();
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
	public CreateTableCommand createTable() {
		return new CreateTableCommand();
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
	public DeleteRelationalTableDataCommand deleteRelationalTableData() {
		return new DeleteRelationalTableDataCommand();
	}

	@Bean
	@Scope("prototype")
	public PurgeTableCommand purgeTable() {
		return new PurgeTableCommand();
	}

	@Bean
	@Scope("prototype")
	public DeleteTableCommand deleteTable() {
		return new DeleteTableCommand();
	}

	@Bean
	@Scope("prototype")
	public CalculateQueryCommand calculateQuery() {
		return new CalculateQueryCommand();
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
}
