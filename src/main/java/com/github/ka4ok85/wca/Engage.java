package com.github.ka4ok85.wca;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.github.ka4ok85.wca.command.AddContactToContactListCommand;
import com.github.ka4ok85.wca.command.AddContactToProgramCommand;
import com.github.ka4ok85.wca.command.AddListColumnCommand;
import com.github.ka4ok85.wca.command.AddRecipientCommand;
import com.github.ka4ok85.wca.command.CalculateQueryCommand;
import com.github.ka4ok85.wca.command.CreateContactListCommand;
import com.github.ka4ok85.wca.command.CreateTableCommand;
import com.github.ka4ok85.wca.command.DeleteListCommand;
import com.github.ka4ok85.wca.command.DeleteRelationalTableDataCommand;
import com.github.ka4ok85.wca.command.RawRecipientDataExportCommand;
import com.github.ka4ok85.wca.command.DeleteTableCommand;
import com.github.ka4ok85.wca.command.DoubleOptInRecipientCommand;
import com.github.ka4ok85.wca.command.ExportListCommand;
import com.github.ka4ok85.wca.command.ExportMailingTemplateCommand;
import com.github.ka4ok85.wca.command.ExportTableCommand;
import com.github.ka4ok85.wca.command.GetAggregateTrackingForMailingCommand;
import com.github.ka4ok85.wca.command.GetAggregateTrackingForOrgCommand;
import com.github.ka4ok85.wca.command.GetAggregateTrackingForUserCommand;
import com.github.ka4ok85.wca.command.GetFolderPathCommand;
import com.github.ka4ok85.wca.command.GetListMetaDataCommand;
import com.github.ka4ok85.wca.command.GetListsCommand;
import com.github.ka4ok85.wca.command.GetMailingTemplatesCommand;
import com.github.ka4ok85.wca.command.GetReportIdByDateCommand;
import com.github.ka4ok85.wca.command.GetSentMailingsForListCommand;
import com.github.ka4ok85.wca.command.GetSentMailingsForOrgCommand;
import com.github.ka4ok85.wca.command.GetSentMailingsForUserCommand;
import com.github.ka4ok85.wca.command.ImportListCommand;
import com.github.ka4ok85.wca.command.ImportTableCommand;
import com.github.ka4ok85.wca.command.InsertUpdateRelationalTableCommand;
import com.github.ka4ok85.wca.command.PreviewMailingCommand;
import com.github.ka4ok85.wca.command.JoinTableCommand;
import com.github.ka4ok85.wca.command.ListRecipientMailingsCommand;
import com.github.ka4ok85.wca.command.OptOutRecipientCommand;
import com.github.ka4ok85.wca.command.PurgeDataCommand;
import com.github.ka4ok85.wca.command.PurgeTableCommand;
import com.github.ka4ok85.wca.command.RemoveRecipientCommand;
import com.github.ka4ok85.wca.command.SelectRecipientDataCommand;
import com.github.ka4ok85.wca.command.UpdateRecipientCommand;
import com.github.ka4ok85.wca.command.WebTrackingDataExportCommand;
import com.github.ka4ok85.wca.command.SetColumnValueCommand;
import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.oauth.OAuthClientImplementation;
import com.github.ka4ok85.wca.options.AddContactToContactListOptions;
import com.github.ka4ok85.wca.options.AddContactToProgramOptions;
import com.github.ka4ok85.wca.options.AddListColumnOptions;
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
import com.github.ka4ok85.wca.options.GetAggregateTrackingForUserOptions;
import com.github.ka4ok85.wca.options.GetFolderPathOptions;
import com.github.ka4ok85.wca.options.GetListMetaDataOptions;
import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.options.GetMailingTemplatesOptions;
import com.github.ka4ok85.wca.options.GetReportIdByDateOptions;
import com.github.ka4ok85.wca.options.GetSentMailingsForListOptions;
import com.github.ka4ok85.wca.options.GetSentMailingsForOrgOptions;
import com.github.ka4ok85.wca.options.GetSentMailingsForUserOptions;
import com.github.ka4ok85.wca.options.ImportListOptions;
import com.github.ka4ok85.wca.options.ImportTableOptions;
import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.options.JoinTableOptions;
import com.github.ka4ok85.wca.options.ListRecipientMailingsOptions;
import com.github.ka4ok85.wca.options.OptOutRecipientOptions;
import com.github.ka4ok85.wca.options.PreviewMailingOptions;
import com.github.ka4ok85.wca.options.PurgeDataOptions;
import com.github.ka4ok85.wca.options.PurgeTableOptions;
import com.github.ka4ok85.wca.options.RawRecipientDataExportOptions;
import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.options.SetColumnValueOptions;
import com.github.ka4ok85.wca.options.UpdateRecipientOptions;
import com.github.ka4ok85.wca.options.WebTrackingDataExportOptions;
import com.github.ka4ok85.wca.response.AddContactToContactListResponse;
import com.github.ka4ok85.wca.response.AddContactToProgramResponse;
import com.github.ka4ok85.wca.response.AddListColumnResponse;
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
import com.github.ka4ok85.wca.response.GetAggregateTrackingForUserResponse;
import com.github.ka4ok85.wca.response.GetFolderPathResponse;
import com.github.ka4ok85.wca.response.GetListMetaDataResponse;
import com.github.ka4ok85.wca.response.GetListsResponse;
import com.github.ka4ok85.wca.response.GetMailingTemplatesResponse;
import com.github.ka4ok85.wca.response.GetReportIdByDateResponse;
import com.github.ka4ok85.wca.response.GetSentMailingsForListResponse;
import com.github.ka4ok85.wca.response.GetSentMailingsForOrgResponse;
import com.github.ka4ok85.wca.response.GetSentMailingsForUserResponse;
import com.github.ka4ok85.wca.response.ImportListResponse;
import com.github.ka4ok85.wca.response.ImportTableResponse;
import com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse;
import com.github.ka4ok85.wca.response.JoinTableResponse;
import com.github.ka4ok85.wca.response.ListRecipientMailingsResponse;
import com.github.ka4ok85.wca.response.OptOutRecipientResponse;
import com.github.ka4ok85.wca.response.PreviewMailingResponse;
import com.github.ka4ok85.wca.response.PurgeDataResponse;
import com.github.ka4ok85.wca.response.PurgeTableResponse;
import com.github.ka4ok85.wca.response.RawRecipientDataExportResponse;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;
import com.github.ka4ok85.wca.response.SetColumnValueResponse;
import com.github.ka4ok85.wca.response.UpdateRecipientResponse;
import com.github.ka4ok85.wca.response.WebTrackingDataExportResponse;
import com.github.ka4ok85.wca.sftp.SFTP;

import org.springframework.retry.annotation.*;

@Configuration
@EnableRetry
public class Engage {
	private OAuthClient oAuthClient;
	private SFTP sftp;

	private GetFolderPathCommand getFolderPathBean = getApplicationContext().getBean(GetFolderPathCommand.class);
	private ExportListCommand exportListBean = getApplicationContext().getBean(ExportListCommand.class);
	private ExportTableCommand exportTableBean = getApplicationContext().getBean(ExportTableCommand.class);
	private CreateContactListCommand createContactListBean = getApplicationContext()
			.getBean(CreateContactListCommand.class);
	private DeleteListCommand deleteListBean = getApplicationContext().getBean(DeleteListCommand.class);
	private SelectRecipientDataCommand selectRecipientDataBean = getApplicationContext()
			.getBean(SelectRecipientDataCommand.class);
	private AddRecipientCommand addRecipientBean = getApplicationContext().getBean(AddRecipientCommand.class);
	private DoubleOptInRecipientCommand doubleOptInRecipientBean = getApplicationContext()
			.getBean(DoubleOptInRecipientCommand.class);
	private UpdateRecipientCommand updateRecipientBean = getApplicationContext().getBean(UpdateRecipientCommand.class);
	private OptOutRecipientCommand optOutRecipientBean = getApplicationContext().getBean(OptOutRecipientCommand.class);
	private RemoveRecipientCommand removeRecipientBean = getApplicationContext().getBean(RemoveRecipientCommand.class);

	private static AnnotationConfigApplicationContext applicationContext;
	{
		getApplicationContext();
	}

	public Engage(int podNumber, String clientId, String clientSecret, String refreshToken) {
		this.oAuthClient = new OAuthClientImplementation(podNumber, clientId, clientSecret, refreshToken);
		this.sftp = new SFTP(this.oAuthClient);
	}

	public Engage(OAuthClient oAuthClient, SFTP sftp) {
		super();
		this.oAuthClient = oAuthClient;
		this.sftp = sftp;
	}

	public ResponseContainer<ExportListResponse> exportList(ExportListOptions options) {
		exportListBean.setoAuthClient(oAuthClient);
		exportListBean.setSftp(sftp);
		ResponseContainer<ExportListResponse> result = exportListBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<ExportTableResponse> exportTable(ExportTableOptions options) {
		exportTableBean.setoAuthClient(oAuthClient);
		exportTableBean.setSftp(sftp);
		ResponseContainer<ExportTableResponse> result = exportTableBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<CreateContactListResponse> createContactList(CreateContactListOptions options) {
		createContactListBean.setoAuthClient(oAuthClient);
		createContactListBean.setSftp(sftp);
		ResponseContainer<CreateContactListResponse> result = createContactListBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteListResponse> deleteList(DeleteListOptions options) {
		deleteListBean.setoAuthClient(oAuthClient);
		deleteListBean.setSftp(sftp);
		ResponseContainer<DeleteListResponse> result = deleteListBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<SelectRecipientDataResponse> selectRecipientData(SelectRecipientDataOptions options) {
		selectRecipientDataBean.setoAuthClient(oAuthClient);
		selectRecipientDataBean.setSftp(sftp);
		ResponseContainer<SelectRecipientDataResponse> result = selectRecipientDataBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddRecipientResponse> addRecipient(AddRecipientOptions options) {
		addRecipientBean.setoAuthClient(oAuthClient);
		addRecipientBean.setSftp(sftp);
		ResponseContainer<AddRecipientResponse> result = addRecipientBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<DoubleOptInRecipientResponse> doubleOptInRecipient(DoubleOptInRecipientOptions options) {
		doubleOptInRecipientBean.setoAuthClient(oAuthClient);
		doubleOptInRecipientBean.setSftp(sftp);
		ResponseContainer<DoubleOptInRecipientResponse> result = doubleOptInRecipientBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<UpdateRecipientResponse> updateRecipient(UpdateRecipientOptions options) {
		updateRecipientBean.setoAuthClient(oAuthClient);
		updateRecipientBean.setSftp(sftp);
		ResponseContainer<UpdateRecipientResponse> result = updateRecipientBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<OptOutRecipientResponse> optOutRecipient(OptOutRecipientOptions options) {
		optOutRecipientBean.setoAuthClient(oAuthClient);
		optOutRecipientBean.setSftp(sftp);
		ResponseContainer<OptOutRecipientResponse> result = optOutRecipientBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<RemoveRecipientResponse> removeRecipient(RemoveRecipientOptions options) {
		removeRecipientBean.setoAuthClient(oAuthClient);
		removeRecipientBean.setSftp(sftp);
		ResponseContainer<RemoveRecipientResponse> result = removeRecipientBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetListsResponse> getLists(GetListsOptions options) {
		GetListsCommand getLists = getApplicationContext().getBean(GetListsCommand.class);
		getLists.setoAuthClient(oAuthClient);
		getLists.setSftp(sftp);
		ResponseContainer<GetListsResponse> result = getLists.executeCommand(options);

		return result;
	}

	public ResponseContainer<CreateTableResponse> createTable(CreateTableOptions options) {
		CreateTableCommand createTable = getApplicationContext().getBean(CreateTableCommand.class);
		createTable.setoAuthClient(oAuthClient);
		createTable.setSftp(sftp);
		ResponseContainer<CreateTableResponse> result = createTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<JoinTableResponse> joinTable(JoinTableOptions options) {
		JoinTableCommand joinTable = getApplicationContext().getBean(JoinTableCommand.class);
		joinTable.setoAuthClient(oAuthClient);
		joinTable.setSftp(sftp);
		ResponseContainer<JoinTableResponse> result = joinTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<InsertUpdateRelationalTableResponse> insertUpdateRelationalTable(
			InsertUpdateRelationalTableOptions options) {
		InsertUpdateRelationalTableCommand insertUpdateRelationalTable = getApplicationContext()
				.getBean(InsertUpdateRelationalTableCommand.class);
		insertUpdateRelationalTable.setoAuthClient(oAuthClient);
		insertUpdateRelationalTable.setSftp(sftp);
		ResponseContainer<InsertUpdateRelationalTableResponse> result = insertUpdateRelationalTable
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteRelationalTableDataResponse> deleteRelationalTableData(
			DeleteRelationalTableDataOptions options) {
		DeleteRelationalTableDataCommand deleteRelationalTableData = getApplicationContext()
				.getBean(DeleteRelationalTableDataCommand.class);
		deleteRelationalTableData.setoAuthClient(oAuthClient);
		deleteRelationalTableData.setSftp(sftp);
		ResponseContainer<DeleteRelationalTableDataResponse> result = deleteRelationalTableData.executeCommand(options);

		return result;
	}

	public ResponseContainer<PurgeTableResponse> purgeTable(PurgeTableOptions options) {
		PurgeTableCommand purgeTable = getApplicationContext().getBean(PurgeTableCommand.class);
		purgeTable.setoAuthClient(oAuthClient);
		purgeTable.setSftp(sftp);
		ResponseContainer<PurgeTableResponse> result = purgeTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteTableResponse> deleteTable(DeleteTableOptions options) {
		DeleteTableCommand deleteTable = getApplicationContext().getBean(DeleteTableCommand.class);
		deleteTable.setoAuthClient(oAuthClient);
		deleteTable.setSftp(sftp);
		ResponseContainer<DeleteTableResponse> result = deleteTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<CalculateQueryResponse> calculateQuery(CalculateQueryOptions options) {
		CalculateQueryCommand calculateQuery = getApplicationContext().getBean(CalculateQueryCommand.class);
		calculateQuery.setoAuthClient(oAuthClient);
		calculateQuery.setSftp(sftp);
		ResponseContainer<CalculateQueryResponse> result = calculateQuery.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetListMetaDataResponse> getListMetaData(GetListMetaDataOptions options) {
		GetListMetaDataCommand getListMetaData = getApplicationContext().getBean(GetListMetaDataCommand.class);
		getListMetaData.setoAuthClient(oAuthClient);
		getListMetaData.setSftp(sftp);
		ResponseContainer<GetListMetaDataResponse> result = getListMetaData.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetMailingTemplatesResponse> getMailingTemplates(GetMailingTemplatesOptions options) {
		GetMailingTemplatesCommand getMailingTemplates = getApplicationContext()
				.getBean(GetMailingTemplatesCommand.class);
		getMailingTemplates.setoAuthClient(oAuthClient);
		getMailingTemplates.setSftp(sftp);
		ResponseContainer<GetMailingTemplatesResponse> result = getMailingTemplates.executeCommand(options);

		return result;
	}

	public ResponseContainer<PurgeDataResponse> purgeData(PurgeDataOptions options) {
		PurgeDataCommand purgeData = getApplicationContext().getBean(PurgeDataCommand.class);
		purgeData.setoAuthClient(oAuthClient);
		purgeData.setSftp(sftp);
		ResponseContainer<PurgeDataResponse> result = purgeData.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetReportIdByDateResponse> getReportIdByDate(GetReportIdByDateOptions options) {
		GetReportIdByDateCommand getReportIdByDate = getApplicationContext().getBean(GetReportIdByDateCommand.class);
		getReportIdByDate.setoAuthClient(oAuthClient);
		getReportIdByDate.setSftp(sftp);
		ResponseContainer<GetReportIdByDateResponse> result = getReportIdByDate.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForOrgResponse> getSentMailingsForOrg(
			GetSentMailingsForOrgOptions options) {
		GetSentMailingsForOrgCommand getSentMailingsForOrg = getApplicationContext()
				.getBean(GetSentMailingsForOrgCommand.class);
		getSentMailingsForOrg.setoAuthClient(oAuthClient);
		getSentMailingsForOrg.setSftp(sftp);
		ResponseContainer<GetSentMailingsForOrgResponse> result = getSentMailingsForOrg.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForUserResponse> getSentMailingsForUser(
			GetSentMailingsForUserOptions options) {
		GetSentMailingsForUserCommand getSentMailingsForUser = getApplicationContext()
				.getBean(GetSentMailingsForUserCommand.class);
		getSentMailingsForUser.setoAuthClient(oAuthClient);
		getSentMailingsForUser.setSftp(sftp);
		ResponseContainer<GetSentMailingsForUserResponse> result = getSentMailingsForUser.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForListResponse> getSentMailingsForList(
			GetSentMailingsForListOptions options) {
		GetSentMailingsForListCommand getSentMailingsForList = getApplicationContext()
				.getBean(GetSentMailingsForListCommand.class);
		getSentMailingsForList.setoAuthClient(oAuthClient);
		getSentMailingsForList.setSftp(sftp);
		ResponseContainer<GetSentMailingsForListResponse> result = getSentMailingsForList.executeCommand(options);

		return result;
	}

	public ResponseContainer<ExportMailingTemplateResponse> exportMailingTemplate(
			ExportMailingTemplateOptions options) {
		ExportMailingTemplateCommand exportMailingTemplate = getApplicationContext()
				.getBean(ExportMailingTemplateCommand.class);
		exportMailingTemplate.setoAuthClient(oAuthClient);
		exportMailingTemplate.setSftp(sftp);
		ResponseContainer<ExportMailingTemplateResponse> result = exportMailingTemplate.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddContactToContactListResponse> addContactToContactList(
			AddContactToContactListOptions options) {
		AddContactToContactListCommand addContactToContactList = getApplicationContext()
				.getBean(AddContactToContactListCommand.class);
		addContactToContactList.setoAuthClient(oAuthClient);
		addContactToContactList.setSftp(sftp);
		ResponseContainer<AddContactToContactListResponse> result = addContactToContactList.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddContactToProgramResponse> addContactToProgram(AddContactToProgramOptions options) {
		AddContactToProgramCommand addContactToProgram = getApplicationContext()
				.getBean(AddContactToProgramCommand.class);
		addContactToProgram.setoAuthClient(oAuthClient);
		addContactToProgram.setSftp(sftp);
		ResponseContainer<AddContactToProgramResponse> result = addContactToProgram.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForOrgResponse> getAggregateTrackingForOrg(
			GetAggregateTrackingForOrgOptions options) {
		GetAggregateTrackingForOrgCommand getAggregateTrackingForOrg = getApplicationContext()
				.getBean(GetAggregateTrackingForOrgCommand.class);
		getAggregateTrackingForOrg.setoAuthClient(oAuthClient);
		getAggregateTrackingForOrg.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForOrgResponse> result = getAggregateTrackingForOrg
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForMailingResponse> getAggregateTrackingForMailing(
			GetAggregateTrackingForMailingOptions options) {
		GetAggregateTrackingForMailingCommand getAggregateTrackingForMailing = getApplicationContext()
				.getBean(GetAggregateTrackingForMailingCommand.class);
		getAggregateTrackingForMailing.setoAuthClient(oAuthClient);
		getAggregateTrackingForMailing.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForMailingResponse> result = getAggregateTrackingForMailing
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForUserResponse> getAggregateTrackingForUser(
			GetAggregateTrackingForUserOptions options) {
		GetAggregateTrackingForUserCommand getAggregateTrackingForUser = getApplicationContext()
				.getBean(GetAggregateTrackingForUserCommand.class);
		getAggregateTrackingForUser.setoAuthClient(oAuthClient);
		getAggregateTrackingForUser.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForUserResponse> result = getAggregateTrackingForUser
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<ImportListResponse> importList(ImportListOptions options) {
		ImportListCommand importList = getApplicationContext().getBean(ImportListCommand.class);
		importList.setoAuthClient(oAuthClient);
		importList.setSftp(sftp);
		ResponseContainer<ImportListResponse> result = importList.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetFolderPathResponse> getFolderPath(GetFolderPathOptions options) {
		getFolderPathBean.setoAuthClient(oAuthClient);
		getFolderPathBean.setSftp(sftp);
		ResponseContainer<GetFolderPathResponse> result = getFolderPathBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<RawRecipientDataExportResponse> rawRecipientDataExport(
			RawRecipientDataExportOptions options) {
		RawRecipientDataExportCommand rawRecipientDataExport = getApplicationContext()
				.getBean(RawRecipientDataExportCommand.class);
		rawRecipientDataExport.setoAuthClient(oAuthClient);
		rawRecipientDataExport.setSftp(sftp);
		ResponseContainer<RawRecipientDataExportResponse> result = rawRecipientDataExport.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddListColumnResponse> addListColumn(AddListColumnOptions options) {
		AddListColumnCommand addListColumn = getApplicationContext().getBean(AddListColumnCommand.class);
		addListColumn.setoAuthClient(oAuthClient);
		addListColumn.setSftp(sftp);
		ResponseContainer<AddListColumnResponse> result = addListColumn.executeCommand(options);

		return result;
	}

	public ResponseContainer<SetColumnValueResponse> setColumnValue(SetColumnValueOptions options) {
		SetColumnValueCommand setColumnValue = getApplicationContext().getBean(SetColumnValueCommand.class);
		setColumnValue.setoAuthClient(oAuthClient);
		setColumnValue.setSftp(sftp);
		ResponseContainer<SetColumnValueResponse> result = setColumnValue.executeCommand(options);

		return result;
	}

	public ResponseContainer<ImportTableResponse> importTable(ImportTableOptions options) {
		ImportTableCommand importTable = getApplicationContext().getBean(ImportTableCommand.class);
		importTable.setoAuthClient(oAuthClient);
		importTable.setSftp(sftp);
		ResponseContainer<ImportTableResponse> result = importTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<ListRecipientMailingsResponse> listRecipientMailings(
			ListRecipientMailingsOptions options) {
		ListRecipientMailingsCommand listRecipientMailings = getApplicationContext()
				.getBean(ListRecipientMailingsCommand.class);
		listRecipientMailings.setoAuthClient(oAuthClient);
		listRecipientMailings.setSftp(sftp);
		ResponseContainer<ListRecipientMailingsResponse> result = listRecipientMailings.executeCommand(options);

		return result;
	}

	public ResponseContainer<WebTrackingDataExportResponse> webTrackingDataExport(
			WebTrackingDataExportOptions options) {
		WebTrackingDataExportCommand webTrackingDataExport = getApplicationContext()
				.getBean(WebTrackingDataExportCommand.class);
		webTrackingDataExport.setoAuthClient(oAuthClient);
		webTrackingDataExport.setSftp(sftp);
		ResponseContainer<WebTrackingDataExportResponse> result = webTrackingDataExport.executeCommand(options);

		return result;
	}

	public ResponseContainer<PreviewMailingResponse> previewMailing(PreviewMailingOptions options) {
		PreviewMailingCommand previewMailing = getApplicationContext().getBean(PreviewMailingCommand.class);
		previewMailing.setoAuthClient(oAuthClient);
		previewMailing.setSftp(sftp);
		ResponseContainer<PreviewMailingResponse> result = previewMailing.executeCommand(options);

		return result;
	}

	private static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
		}

		return applicationContext;
	}

	protected void setGetFolderPathBean(GetFolderPathCommand getFolderPathBean) {
		this.getFolderPathBean = getFolderPathBean;
	}

	protected void setExportListBean(ExportListCommand exportListBean) {
		this.exportListBean = exportListBean;
	}

	protected void setExportTableBean(ExportTableCommand exportTableBean) {
		this.exportTableBean = exportTableBean;
	}

	protected void setCreateContactListBean(CreateContactListCommand createContactListBean) {
		this.createContactListBean = createContactListBean;
	}

	protected void setDeleteListBean(DeleteListCommand deleteListBean) {
		this.deleteListBean = deleteListBean;
	}

	protected void setSelectRecipientDataBean(SelectRecipientDataCommand selectRecipientDataBean) {
		this.selectRecipientDataBean = selectRecipientDataBean;
	}

	protected void setAddRecipientBean(AddRecipientCommand addRecipientBean) {
		this.addRecipientBean = addRecipientBean;
	}

	protected void setDoubleOptInRecipientBean(DoubleOptInRecipientCommand doubleOptInRecipientBean) {
		this.doubleOptInRecipientBean = doubleOptInRecipientBean;
	}

	protected void setUpdateRecipientBean(UpdateRecipientCommand updateRecipientBean) {
		this.updateRecipientBean = updateRecipientBean;
	}

	protected void setOptOutRecipientBean(OptOutRecipientCommand optOutRecipientBean) {
		this.optOutRecipientBean = optOutRecipientBean;
	}

	protected void setRemoveRecipientBean(RemoveRecipientCommand removeRecipientBean) {
		this.removeRecipientBean = removeRecipientBean;
	}

}
