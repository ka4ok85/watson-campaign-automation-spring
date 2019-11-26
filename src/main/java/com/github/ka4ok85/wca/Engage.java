package com.github.ka4ok85.wca;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.github.ka4ok85.wca.command.*;
import com.github.ka4ok85.wca.options.*;
import com.github.ka4ok85.wca.response.*;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.oauth.OAuthClientImplementation;
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
	private GetListsCommand getListsBean = getApplicationContext().getBean(GetListsCommand.class);
	private CreateTableCommand createTableBean = getApplicationContext().getBean(CreateTableCommand.class);
	private JoinTableCommand joinTableBean = getApplicationContext().getBean(JoinTableCommand.class);
	private InsertUpdateRelationalTableCommand insertUpdateRelationalTableBean = getApplicationContext()
			.getBean(InsertUpdateRelationalTableCommand.class);
	private DeleteRelationalTableDataCommand deleteRelationalTableDataBean = getApplicationContext()
			.getBean(DeleteRelationalTableDataCommand.class);
	private PurgeTableCommand purgeTableBean = getApplicationContext().getBean(PurgeTableCommand.class);
	private RawRecipientDataExportCommand rawRecipientDataExportBean = getApplicationContext()
			.getBean(RawRecipientDataExportCommand.class);
	private AddListColumnCommand addListColumnBean = getApplicationContext().getBean(AddListColumnCommand.class);
	private SetColumnValueCommand setColumnValueBean = getApplicationContext().getBean(SetColumnValueCommand.class);
	private ScheduleMailingCommand scheduleMailingBean = getApplicationContext().getBean(ScheduleMailingCommand.class);
	private DeleteTableCommand deleteTableBean = getApplicationContext().getBean(DeleteTableCommand.class);
	private CalculateQueryCommand calculateQueryBean = getApplicationContext().getBean(CalculateQueryCommand.class);
	private GetListMetaDataCommand getListMetaDataBean = getApplicationContext().getBean(GetListMetaDataCommand.class);
	private GetMailingTemplatesCommand getMailingTemplatesBean = getApplicationContext()
			.getBean(GetMailingTemplatesCommand.class);
	private PurgeDataCommand purgeDataBean = getApplicationContext().getBean(PurgeDataCommand.class);
	private GetReportIdByDateCommand getReportIdByDateBean = getApplicationContext()
			.getBean(GetReportIdByDateCommand.class);
	private GetSentMailingsForOrgCommand getSentMailingsForOrgBean = getApplicationContext()
			.getBean(GetSentMailingsForOrgCommand.class);
	private GetSentMailingsForUserCommand getSentMailingsForUserBean = getApplicationContext()
			.getBean(GetSentMailingsForUserCommand.class);
	private GetSentMailingsForListCommand getSentMailingsForListBean = getApplicationContext()
			.getBean(GetSentMailingsForListCommand.class);
	private ExportMailingTemplateCommand exportMailingTemplateBean = getApplicationContext()
			.getBean(ExportMailingTemplateCommand.class);
	private AddContactToContactListCommand addContactToContactListBean = getApplicationContext()
			.getBean(AddContactToContactListCommand.class);
	private AddContactToProgramCommand addContactToProgramBean = getApplicationContext()
			.getBean(AddContactToProgramCommand.class);
	private GetAggregateTrackingForOrgCommand getAggregateTrackingForOrgBean = getApplicationContext()
			.getBean(GetAggregateTrackingForOrgCommand.class);
	private GetAggregateTrackingForMailingCommand getAggregateTrackingForMailingBean = getApplicationContext()
			.getBean(GetAggregateTrackingForMailingCommand.class);
	private GetAggregateTrackingForUserCommand getAggregateTrackingForUserBean = getApplicationContext()
			.getBean(GetAggregateTrackingForUserCommand.class);
	private ImportListCommand importListBean = getApplicationContext().getBean(ImportListCommand.class);
	private ImportTableCommand importTableBean = getApplicationContext().getBean(ImportTableCommand.class);
	private ListRecipientMailingsCommand listRecipientMailingsBean = getApplicationContext()
			.getBean(ListRecipientMailingsCommand.class);
	private WebTrackingDataExportCommand webTrackingDataExportBean = getApplicationContext()
			.getBean(WebTrackingDataExportCommand.class);
	private PreviewMailingCommand previewMailingBean = getApplicationContext().getBean(PreviewMailingCommand.class);

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
		getListsBean.setoAuthClient(oAuthClient);
		getListsBean.setSftp(sftp);
		ResponseContainer<GetListsResponse> result = getListsBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<CreateTableResponse> createTable(CreateTableOptions options) {
		createTableBean.setoAuthClient(oAuthClient);
		createTableBean.setSftp(sftp);
		ResponseContainer<CreateTableResponse> result = createTableBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<JoinTableResponse> joinTable(JoinTableOptions options) {
		joinTableBean.setoAuthClient(oAuthClient);
		joinTableBean.setSftp(sftp);
		ResponseContainer<JoinTableResponse> result = joinTableBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<InsertUpdateRelationalTableResponse> insertUpdateRelationalTable(
			InsertUpdateRelationalTableOptions options) {
		insertUpdateRelationalTableBean.setoAuthClient(oAuthClient);
		insertUpdateRelationalTableBean.setSftp(sftp);
		ResponseContainer<InsertUpdateRelationalTableResponse> result = insertUpdateRelationalTableBean
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteRelationalTableDataResponse> deleteRelationalTableData(
			DeleteRelationalTableDataOptions options) {
		deleteRelationalTableDataBean.setoAuthClient(oAuthClient);
		deleteRelationalTableDataBean.setSftp(sftp);
		ResponseContainer<DeleteRelationalTableDataResponse> result = deleteRelationalTableDataBean
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<PurgeTableResponse> purgeTable(PurgeTableOptions options) {
		purgeTableBean.setoAuthClient(oAuthClient);
		purgeTableBean.setSftp(sftp);
		ResponseContainer<PurgeTableResponse> result = purgeTableBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteTableResponse> deleteTable(DeleteTableOptions options) {
		deleteTableBean.setoAuthClient(oAuthClient);
		deleteTableBean.setSftp(sftp);
		ResponseContainer<DeleteTableResponse> result = deleteTableBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<CalculateQueryResponse> calculateQuery(CalculateQueryOptions options) {
		calculateQueryBean.setoAuthClient(oAuthClient);
		calculateQueryBean.setSftp(sftp);
		ResponseContainer<CalculateQueryResponse> result = calculateQueryBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetListMetaDataResponse> getListMetaData(GetListMetaDataOptions options) {
		getListMetaDataBean.setoAuthClient(oAuthClient);
		getListMetaDataBean.setSftp(sftp);
		ResponseContainer<GetListMetaDataResponse> result = getListMetaDataBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetMailingTemplatesResponse> getMailingTemplates(GetMailingTemplatesOptions options) {

		getMailingTemplatesBean.setoAuthClient(oAuthClient);
		getMailingTemplatesBean.setSftp(sftp);
		ResponseContainer<GetMailingTemplatesResponse> result = getMailingTemplatesBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<PurgeDataResponse> purgeData(PurgeDataOptions options) {
		purgeDataBean.setoAuthClient(oAuthClient);
		purgeDataBean.setSftp(sftp);
		ResponseContainer<PurgeDataResponse> result = purgeDataBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetReportIdByDateResponse> getReportIdByDate(GetReportIdByDateOptions options) {
		getReportIdByDateBean.setoAuthClient(oAuthClient);
		getReportIdByDateBean.setSftp(sftp);
		ResponseContainer<GetReportIdByDateResponse> result = getReportIdByDateBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForOrgResponse> getSentMailingsForOrg(
			GetSentMailingsForOrgOptions options) {

		getSentMailingsForOrgBean.setoAuthClient(oAuthClient);
		getSentMailingsForOrgBean.setSftp(sftp);
		ResponseContainer<GetSentMailingsForOrgResponse> result = getSentMailingsForOrgBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForUserResponse> getSentMailingsForUser(
			GetSentMailingsForUserOptions options) {

		getSentMailingsForUserBean.setoAuthClient(oAuthClient);
		getSentMailingsForUserBean.setSftp(sftp);
		ResponseContainer<GetSentMailingsForUserResponse> result = getSentMailingsForUserBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetSentMailingsForListResponse> getSentMailingsForList(
			GetSentMailingsForListOptions options) {

		getSentMailingsForListBean.setoAuthClient(oAuthClient);
		getSentMailingsForListBean.setSftp(sftp);
		ResponseContainer<GetSentMailingsForListResponse> result = getSentMailingsForListBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<ExportMailingTemplateResponse> exportMailingTemplate(
			ExportMailingTemplateOptions options) {

		exportMailingTemplateBean.setoAuthClient(oAuthClient);
		exportMailingTemplateBean.setSftp(sftp);
		ResponseContainer<ExportMailingTemplateResponse> result = exportMailingTemplateBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddContactToContactListResponse> addContactToContactList(
			AddContactToContactListOptions options) {

		addContactToContactListBean.setoAuthClient(oAuthClient);
		addContactToContactListBean.setSftp(sftp);
		ResponseContainer<AddContactToContactListResponse> result = addContactToContactListBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddContactToProgramResponse> addContactToProgram(AddContactToProgramOptions options) {

		addContactToProgramBean.setoAuthClient(oAuthClient);
		addContactToProgramBean.setSftp(sftp);
		ResponseContainer<AddContactToProgramResponse> result = addContactToProgramBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForOrgResponse> getAggregateTrackingForOrg(
			GetAggregateTrackingForOrgOptions options) {

		getAggregateTrackingForOrgBean.setoAuthClient(oAuthClient);
		getAggregateTrackingForOrgBean.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForOrgResponse> result = getAggregateTrackingForOrgBean
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForMailingResponse> getAggregateTrackingForMailing(
			GetAggregateTrackingForMailingOptions options) {

		getAggregateTrackingForMailingBean.setoAuthClient(oAuthClient);
		getAggregateTrackingForMailingBean.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForMailingResponse> result = getAggregateTrackingForMailingBean
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<GetAggregateTrackingForUserResponse> getAggregateTrackingForUser(
			GetAggregateTrackingForUserOptions options) {

		getAggregateTrackingForUserBean.setoAuthClient(oAuthClient);
		getAggregateTrackingForUserBean.setSftp(sftp);
		ResponseContainer<GetAggregateTrackingForUserResponse> result = getAggregateTrackingForUserBean
				.executeCommand(options);

		return result;
	}

	public ResponseContainer<ImportListResponse> importList(ImportListOptions options) {
		importListBean.setoAuthClient(oAuthClient);
		importListBean.setSftp(sftp);
		ResponseContainer<ImportListResponse> result = importListBean.executeCommand(options);

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
		rawRecipientDataExportBean.setoAuthClient(oAuthClient);
		rawRecipientDataExportBean.setSftp(sftp);
		ResponseContainer<RawRecipientDataExportResponse> result = rawRecipientDataExportBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<AddListColumnResponse> addListColumn(AddListColumnOptions options) {
		addListColumnBean.setoAuthClient(oAuthClient);
		addListColumnBean.setSftp(sftp);
		ResponseContainer<AddListColumnResponse> result = addListColumnBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<SetColumnValueResponse> setColumnValue(SetColumnValueOptions options) {
		setColumnValueBean.setoAuthClient(oAuthClient);
		setColumnValueBean.setSftp(sftp);
		ResponseContainer<SetColumnValueResponse> result = setColumnValueBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<ImportTableResponse> importTable(ImportTableOptions options) {
		importTableBean.setoAuthClient(oAuthClient);
		importTableBean.setSftp(sftp);
		ResponseContainer<ImportTableResponse> result = importTableBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<ListRecipientMailingsResponse> listRecipientMailings(
			ListRecipientMailingsOptions options) {

		listRecipientMailingsBean.setoAuthClient(oAuthClient);
		listRecipientMailingsBean.setSftp(sftp);
		ResponseContainer<ListRecipientMailingsResponse> result = listRecipientMailingsBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<WebTrackingDataExportResponse> webTrackingDataExport(
			WebTrackingDataExportOptions options) {

		webTrackingDataExportBean.setoAuthClient(oAuthClient);
		webTrackingDataExportBean.setSftp(sftp);
		ResponseContainer<WebTrackingDataExportResponse> result = webTrackingDataExportBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<PreviewMailingResponse> previewMailing(PreviewMailingOptions options) {
		previewMailingBean.setoAuthClient(oAuthClient);
		previewMailingBean.setSftp(sftp);
		ResponseContainer<PreviewMailingResponse> result = previewMailingBean.executeCommand(options);

		return result;
	}

	public ResponseContainer<ScheduleMailingResponse> scheduleMailing(ScheduleMailingOptions options) {
		scheduleMailingBean.setoAuthClient(oAuthClient);
		scheduleMailingBean.setSftp(sftp);
		ResponseContainer<ScheduleMailingResponse> result = scheduleMailingBean.executeCommand(options);
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

	protected void setGetListsBean(GetListsCommand getListsBean) {
		this.getListsBean = getListsBean;
	}

	protected void setCreateTableBean(CreateTableCommand createTableBean) {
		this.createTableBean = createTableBean;
	}

	protected void setJoinTableBean(JoinTableCommand joinTableBean) {
		this.joinTableBean = joinTableBean;
	}

	protected void setInsertUpdateRelationalTableBean(
			InsertUpdateRelationalTableCommand insertUpdateRelationalTableBean) {
		this.insertUpdateRelationalTableBean = insertUpdateRelationalTableBean;
	}

	protected void setDeleteRelationalTableDataBean(DeleteRelationalTableDataCommand deleteRelationalTableDataBean) {
		this.deleteRelationalTableDataBean = deleteRelationalTableDataBean;
	}

	protected void setPurgeTableBean(PurgeTableCommand purgeTableBean) {
		this.purgeTableBean = purgeTableBean;
	}

	protected void setRawRecipientDataExportBean(RawRecipientDataExportCommand rawRecipientDataExportBean) {
		this.rawRecipientDataExportBean = rawRecipientDataExportBean;
	}

	protected void setAddListColumnBean(AddListColumnCommand addListColumnBean) {
		this.addListColumnBean = addListColumnBean;
	}

	protected void setSetColumnValueBean(SetColumnValueCommand setColumnValueBean) {
		this.setColumnValueBean = setColumnValueBean;
	}

	protected void setScheduleMailingBean(ScheduleMailingCommand scheduleMailingBean) {
		this.scheduleMailingBean = scheduleMailingBean;
	}
}
