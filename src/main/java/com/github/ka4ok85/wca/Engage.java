package com.github.ka4ok85.wca;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.ka4ok85.wca.command.AddContactToContactListCommand;
import com.github.ka4ok85.wca.command.AddContactToProgramCommand;
import com.github.ka4ok85.wca.command.AddRecipientCommand;
import com.github.ka4ok85.wca.command.CalculateQueryCommand;
import com.github.ka4ok85.wca.command.CreateContactListCommand;
import com.github.ka4ok85.wca.command.CreateTableCommand;
import com.github.ka4ok85.wca.command.DeleteListCommand;
import com.github.ka4ok85.wca.command.DeleteRelationalTableDataCommand;
import com.github.ka4ok85.wca.command.DeleteTableCommand;
import com.github.ka4ok85.wca.command.DoubleOptInRecipientCommand;
import com.github.ka4ok85.wca.command.ExportListCommand;
import com.github.ka4ok85.wca.command.ExportMailingTemplateCommand;
import com.github.ka4ok85.wca.command.ExportTableCommand;
import com.github.ka4ok85.wca.command.GetAggregateTrackingForMailingCommand;
import com.github.ka4ok85.wca.command.GetAggregateTrackingForOrgCommand;
import com.github.ka4ok85.wca.command.GetListMetaDataCommand;
import com.github.ka4ok85.wca.command.GetListsCommand;
import com.github.ka4ok85.wca.command.GetMailingTemplatesCommand;
import com.github.ka4ok85.wca.command.GetReportIdByDateCommand;
import com.github.ka4ok85.wca.command.GetSentMailingsForListCommand;
import com.github.ka4ok85.wca.command.GetSentMailingsForOrgCommand;
import com.github.ka4ok85.wca.command.GetSentMailingsForUserCommand;
import com.github.ka4ok85.wca.command.InsertUpdateRelationalTableCommand;
import com.github.ka4ok85.wca.command.JoinTableCommand;
import com.github.ka4ok85.wca.command.OptOutRecipientCommand;
import com.github.ka4ok85.wca.command.PurgeDataCommand;
import com.github.ka4ok85.wca.command.PurgeTableCommand;
import com.github.ka4ok85.wca.command.RemoveRecipientCommand;
import com.github.ka4ok85.wca.command.SelectRecipientDataCommand;
import com.github.ka4ok85.wca.command.UpdateRecipientCommand;
import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.oauth.OAuthClientImplementation;
import com.github.ka4ok85.wca.options.AddContactToContactListOptions;
import com.github.ka4ok85.wca.options.AddContactToProgramOptions;
import com.github.ka4ok85.wca.options.AddRecipientOptions;
import com.github.ka4ok85.wca.options.CalculateQueryOptions;
import com.github.ka4ok85.wca.options.CreateContactListOptions;
import com.github.ka4ok85.wca.options.CreateTableOptions;
import com.github.ka4ok85.wca.options.DeleteListOptions;
import com.github.ka4ok85.wca.options.DeleteRelationalTableDataOptions;
import com.github.ka4ok85.wca.options.DeleteTableOptions;
import com.github.ka4ok85.wca.options.DoubleOptInRecipientOptions;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.options.ExportMailingTemplateOptions;
import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.options.GetAggregateTrackingForMailingOptions;
import com.github.ka4ok85.wca.options.GetAggregateTrackingForOrgOptions;
import com.github.ka4ok85.wca.options.GetListMetaDataOptions;
import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.options.GetMailingTemplatesOptions;
import com.github.ka4ok85.wca.options.GetReportIdByDateOptions;
import com.github.ka4ok85.wca.options.GetSentMailingsForListOptions;
import com.github.ka4ok85.wca.options.GetSentMailingsForOrgOptions;
import com.github.ka4ok85.wca.options.GetSentMailingsForUserOptions;
import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.options.JoinTableOptions;
import com.github.ka4ok85.wca.options.OptOutRecipientOptions;
import com.github.ka4ok85.wca.options.PurgeDataOptions;
import com.github.ka4ok85.wca.options.PurgeTableOptions;
import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.options.UpdateRecipientOptions;
import com.github.ka4ok85.wca.response.AddContactToContactListResponse;
import com.github.ka4ok85.wca.response.AddContactToProgramResponse;
import com.github.ka4ok85.wca.response.AddRecipientResponse;
import com.github.ka4ok85.wca.response.CalculateQueryResponse;
import com.github.ka4ok85.wca.response.CreateContactListResponse;
import com.github.ka4ok85.wca.response.CreateTableResponse;
import com.github.ka4ok85.wca.response.DeleteListResponse;
import com.github.ka4ok85.wca.response.DeleteRelationalTableDataResponse;
import com.github.ka4ok85.wca.response.DeleteTableResponse;
import com.github.ka4ok85.wca.response.DoubleOptInRecipientResponse;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.ExportMailingTemplateResponse;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.response.GetAggregateTrackingForMailingResponse;
import com.github.ka4ok85.wca.response.GetAggregateTrackingForOrgResponse;
import com.github.ka4ok85.wca.response.GetListMetaDataResponse;
import com.github.ka4ok85.wca.response.GetListsResponse;
import com.github.ka4ok85.wca.response.GetMailingTemplatesResponse;
import com.github.ka4ok85.wca.response.GetReportIdByDateResponse;
import com.github.ka4ok85.wca.response.GetSentMailingsForListResponse;
import com.github.ka4ok85.wca.response.GetSentMailingsForOrgResponse;
import com.github.ka4ok85.wca.response.GetSentMailingsForUserResponse;
import com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse;
import com.github.ka4ok85.wca.response.JoinTableResponse;
import com.github.ka4ok85.wca.response.OptOutRecipientResponse;
import com.github.ka4ok85.wca.response.PurgeDataResponse;
import com.github.ka4ok85.wca.response.PurgeTableResponse;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;
import com.github.ka4ok85.wca.response.UpdateRecipientResponse;
import com.github.ka4ok85.wca.sftp.SFTP;

public class Engage {
	private OAuthClient oAuthClient;
	private SFTP sftp;

	private static AnnotationConfigApplicationContext applicationContext;

	{
		getApplicationContext();
	}

	public Engage(int podNumber, String clientId, String clientSecret, String refreshToken) {
		this.oAuthClient = new OAuthClientImplementation(podNumber, clientId, clientSecret, refreshToken);
		this.sftp = new SFTP(this.oAuthClient);
	}

	public ResponseContainer<ExportListResponse> exportList(ExportListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		ExportListCommand exportList = getApplicationContext().getBean(ExportListCommand.class);
		exportList.setoAuthClient(oAuthClient);
		exportList.setSftp(sftp);
		ResponseContainer<ExportListResponse> result = exportList.executeCommand(options);

		return result;
	}

	public ResponseContainer<ExportTableResponse> exportTable(ExportTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		ExportTableCommand exportTable = getApplicationContext().getBean(ExportTableCommand.class);
		exportTable.setoAuthClient(oAuthClient);
		exportTable.setSftp(sftp);
		ResponseContainer<ExportTableResponse> result = exportTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<CreateContactListResponse> createContactList(CreateContactListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		CreateContactListCommand createContactList = getApplicationContext().getBean(CreateContactListCommand.class);
		createContactList.setoAuthClient(oAuthClient);
		createContactList.setSftp(sftp);
		ResponseContainer<CreateContactListResponse> result = createContactList.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteListResponse> deleteList(DeleteListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DeleteListCommand deleteList = getApplicationContext().getBean(DeleteListCommand.class);
		deleteList.setoAuthClient(oAuthClient);
		deleteList.setSftp(sftp);
		ResponseContainer<DeleteListResponse> result = deleteList.executeCommand(options);

		return result;
	}

	public ResponseContainer<SelectRecipientDataResponse> selectRecipientData(SelectRecipientDataOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		SelectRecipientDataCommand selectRecipientData = getApplicationContext()
				.getBean(SelectRecipientDataCommand.class);
		selectRecipientData.setoAuthClient(oAuthClient);
		selectRecipientData.setSftp(sftp);
		ResponseContainer<SelectRecipientDataResponse> result = selectRecipientData.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddRecipientResponse> addRecipient(AddRecipientOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		AddRecipientCommand addRecipient = getApplicationContext().getBean(AddRecipientCommand.class);
		addRecipient.setoAuthClient(oAuthClient);
		addRecipient.setSftp(sftp);
		ResponseContainer<AddRecipientResponse> result = addRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<DoubleOptInRecipientResponse> doubleOptInRecipient(DoubleOptInRecipientOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DoubleOptInRecipientCommand doubleOptInRecipient = getApplicationContext()
				.getBean(DoubleOptInRecipientCommand.class);
		doubleOptInRecipient.setoAuthClient(oAuthClient);
		doubleOptInRecipient.setSftp(sftp);
		ResponseContainer<DoubleOptInRecipientResponse> result = doubleOptInRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<UpdateRecipientResponse> updateRecipient(UpdateRecipientOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		UpdateRecipientCommand updateRecipient = getApplicationContext().getBean(UpdateRecipientCommand.class);
		updateRecipient.setoAuthClient(oAuthClient);
		updateRecipient.setSftp(sftp);
		ResponseContainer<UpdateRecipientResponse> result = updateRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<OptOutRecipientResponse> optOutRecipient(OptOutRecipientOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		OptOutRecipientCommand optOutRecipient = getApplicationContext().getBean(OptOutRecipientCommand.class);
		optOutRecipient.setoAuthClient(oAuthClient);
		optOutRecipient.setSftp(sftp);
		ResponseContainer<OptOutRecipientResponse> result = optOutRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<RemoveRecipientResponse> removeRecipient(RemoveRecipientOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		RemoveRecipientCommand removeRecipient = getApplicationContext().getBean(RemoveRecipientCommand.class);
		removeRecipient.setoAuthClient(oAuthClient);
		removeRecipient.setSftp(sftp);
		ResponseContainer<RemoveRecipientResponse> result = removeRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetListsResponse> getLists(GetListsOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetListsCommand getLists = getApplicationContext().getBean(GetListsCommand.class);
		getLists.setoAuthClient(oAuthClient);
		getLists.setSftp(sftp);
		ResponseContainer<GetListsResponse> result = getLists.executeCommand(options);

		return result;
	}

	public ResponseContainer<CreateTableResponse> createTable(CreateTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		CreateTableCommand createTable = getApplicationContext().getBean(CreateTableCommand.class);
		createTable.setoAuthClient(oAuthClient);
		createTable.setSftp(sftp);
		ResponseContainer<CreateTableResponse> result = createTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<JoinTableResponse> joinTable(JoinTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		JoinTableCommand joinTable = getApplicationContext().getBean(JoinTableCommand.class);
		joinTable.setoAuthClient(oAuthClient);
		joinTable.setSftp(sftp);
		ResponseContainer<JoinTableResponse> result = joinTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<InsertUpdateRelationalTableResponse> insertUpdateRelationalTable(
			InsertUpdateRelationalTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		InsertUpdateRelationalTableCommand insertUpdateRelationalTable = getApplicationContext()
				.getBean(InsertUpdateRelationalTableCommand.class);
		insertUpdateRelationalTable.setoAuthClient(oAuthClient);
		insertUpdateRelationalTable.setSftp(sftp);
		ResponseContainer<InsertUpdateRelationalTableResponse> result = insertUpdateRelationalTable
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteRelationalTableDataResponse> deleteRelationalTableData(
			DeleteRelationalTableDataOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DeleteRelationalTableDataCommand deleteRelationalTableData = getApplicationContext()
				.getBean(DeleteRelationalTableDataCommand.class);
		deleteRelationalTableData.setoAuthClient(oAuthClient);
		deleteRelationalTableData.setSftp(sftp);
		ResponseContainer<DeleteRelationalTableDataResponse> result = deleteRelationalTableData.executeCommand(options);

		return result;
	}

	public ResponseContainer<PurgeTableResponse> purgeTable(PurgeTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		PurgeTableCommand purgeTable = getApplicationContext().getBean(PurgeTableCommand.class);
		purgeTable.setoAuthClient(oAuthClient);
		purgeTable.setSftp(sftp);
		ResponseContainer<PurgeTableResponse> result = purgeTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteTableResponse> deleteTable(DeleteTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DeleteTableCommand deleteTable = getApplicationContext().getBean(DeleteTableCommand.class);
		deleteTable.setoAuthClient(oAuthClient);
		deleteTable.setSftp(sftp);
		ResponseContainer<DeleteTableResponse> result = deleteTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<CalculateQueryResponse> calculateQuery(CalculateQueryOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		CalculateQueryCommand calculateQuery = getApplicationContext().getBean(CalculateQueryCommand.class);
		calculateQuery.setoAuthClient(oAuthClient);
		calculateQuery.setSftp(sftp);
		ResponseContainer<CalculateQueryResponse> result = calculateQuery.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetListMetaDataResponse> getListMetaData(GetListMetaDataOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetListMetaDataCommand getListMetaData = getApplicationContext().getBean(GetListMetaDataCommand.class);
		getListMetaData.setoAuthClient(oAuthClient);
		getListMetaData.setSftp(sftp);
		ResponseContainer<GetListMetaDataResponse> result = getListMetaData.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetMailingTemplatesResponse> getMailingTemplates(GetMailingTemplatesOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetMailingTemplatesCommand getMailingTemplates = getApplicationContext()
				.getBean(GetMailingTemplatesCommand.class);
		getMailingTemplates.setoAuthClient(oAuthClient);
		getMailingTemplates.setSftp(sftp);
		ResponseContainer<GetMailingTemplatesResponse> result = getMailingTemplates.executeCommand(options);

		return result;
	}

	public ResponseContainer<PurgeDataResponse> purgeData(PurgeDataOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		PurgeDataCommand purgeData = getApplicationContext().getBean(PurgeDataCommand.class);
		purgeData.setoAuthClient(oAuthClient);
		purgeData.setSftp(sftp);
		ResponseContainer<PurgeDataResponse> result = purgeData.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetReportIdByDateResponse> getReportIdByDate(GetReportIdByDateOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetReportIdByDateCommand getReportIdByDate = getApplicationContext().getBean(GetReportIdByDateCommand.class);
		getReportIdByDate.setoAuthClient(oAuthClient);
		getReportIdByDate.setSftp(sftp);
		ResponseContainer<GetReportIdByDateResponse> result = getReportIdByDate.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForOrgResponse> getSentMailingsForOrg(GetSentMailingsForOrgOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetSentMailingsForOrgCommand getSentMailingsForOrg = getApplicationContext()
				.getBean(GetSentMailingsForOrgCommand.class);
		getSentMailingsForOrg.setoAuthClient(oAuthClient);
		getSentMailingsForOrg.setSftp(sftp);
		ResponseContainer<GetSentMailingsForOrgResponse> result = getSentMailingsForOrg.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForUserResponse> getSentMailingsForUser(
			GetSentMailingsForUserOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetSentMailingsForUserCommand getSentMailingsForUser = getApplicationContext()
				.getBean(GetSentMailingsForUserCommand.class);
		getSentMailingsForUser.setoAuthClient(oAuthClient);
		getSentMailingsForUser.setSftp(sftp);
		ResponseContainer<GetSentMailingsForUserResponse> result = getSentMailingsForUser.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForListResponse> getSentMailingsForList(
			GetSentMailingsForListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetSentMailingsForListCommand getSentMailingsForList = getApplicationContext()
				.getBean(GetSentMailingsForListCommand.class);
		getSentMailingsForList.setoAuthClient(oAuthClient);
		getSentMailingsForList.setSftp(sftp);
		ResponseContainer<GetSentMailingsForListResponse> result = getSentMailingsForList.executeCommand(options);

		return result;
	}

	public ResponseContainer<ExportMailingTemplateResponse> exportMailingTemplate(ExportMailingTemplateOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		ExportMailingTemplateCommand exportMailingTemplate = getApplicationContext()
				.getBean(ExportMailingTemplateCommand.class);
		exportMailingTemplate.setoAuthClient(oAuthClient);
		exportMailingTemplate.setSftp(sftp);
		ResponseContainer<ExportMailingTemplateResponse> result = exportMailingTemplate.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddContactToContactListResponse> addContactToContactList(
			AddContactToContactListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		AddContactToContactListCommand addContactToContactList = getApplicationContext()
				.getBean(AddContactToContactListCommand.class);
		addContactToContactList.setoAuthClient(oAuthClient);
		addContactToContactList.setSftp(sftp);
		ResponseContainer<AddContactToContactListResponse> result = addContactToContactList.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddContactToProgramResponse> addContactToProgram(AddContactToProgramOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		AddContactToProgramCommand addContactToProgram = getApplicationContext()
				.getBean(AddContactToProgramCommand.class);
		addContactToProgram.setoAuthClient(oAuthClient);
		addContactToProgram.setSftp(sftp);
		ResponseContainer<AddContactToProgramResponse> result = addContactToProgram.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForOrgResponse> getAggregateTrackingForOrg(
			GetAggregateTrackingForOrgOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetAggregateTrackingForOrgCommand getAggregateTrackingForOrg = getApplicationContext()
				.getBean(GetAggregateTrackingForOrgCommand.class);
		getAggregateTrackingForOrg.setoAuthClient(oAuthClient);
		getAggregateTrackingForOrg.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForOrgResponse> result = getAggregateTrackingForOrg
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForMailingResponse> getAggregateTrackingForMailing(
			GetAggregateTrackingForMailingOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetAggregateTrackingForMailingCommand getAggregateTrackingForMailing = getApplicationContext()
				.getBean(GetAggregateTrackingForMailingCommand.class);
		getAggregateTrackingForMailing.setoAuthClient(oAuthClient);
		getAggregateTrackingForMailing.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForMailingResponse> result = getAggregateTrackingForMailing
				.executeCommand(options);

		return result;
	}

	private static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
		}

		return applicationContext;
	}
}
