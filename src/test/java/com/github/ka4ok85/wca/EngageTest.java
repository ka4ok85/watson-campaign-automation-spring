package com.github.ka4ok85.wca;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ka4ok85.wca.command.*;
import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.ColumnValueAction;
import com.github.ka4ok85.wca.constants.GetFolderPathObjectType;
import com.github.ka4ok85.wca.constants.ListColumnType;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.*;
import com.github.ka4ok85.wca.response.*;
import com.github.ka4ok85.wca.sftp.SFTP;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class EngageTest {

	@Test
	public void testGetFolderPath() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		GetFolderPathOptions options = new GetFolderPathOptions(GetFolderPathObjectType.Data, 1L);
		GetFolderPathResponse response = new GetFolderPathResponse();

		GetFolderPathCommand getFolderPathBean = mock(GetFolderPathCommand.class);
		when(getFolderPathBean.executeCommand(options))
				.thenReturn(new ResponseContainer<GetFolderPathResponse>(response));

		engage.setGetFolderPathBean(getFolderPathBean);

		ResponseContainer<GetFolderPathResponse> responseContainer = engage.getFolderPath(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), GetFolderPathResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testExportList() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		ExportListOptions options = new ExportListOptions(1L);
		ExportListResponse response = new ExportListResponse();

		ExportListCommand exportListBean = mock(ExportListCommand.class);
		when(exportListBean.executeCommand(options)).thenReturn(new ResponseContainer<ExportListResponse>(response));

		engage.setExportListBean(exportListBean);

		ResponseContainer<ExportListResponse> responseContainer = engage.exportList(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), ExportListResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testExportTable() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		ExportTableOptions options = new ExportTableOptions(1L);
		ExportTableResponse response = new ExportTableResponse();

		ExportTableCommand exportTableBean = mock(ExportTableCommand.class);
		when(exportTableBean.executeCommand(options)).thenReturn(new ResponseContainer<ExportTableResponse>(response));

		engage.setExportTableBean(exportTableBean);

		ResponseContainer<ExportTableResponse> responseContainer = engage.exportTable(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), ExportTableResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testCreateContactList() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		CreateContactListOptions options = new CreateContactListOptions(1L, "test list", Visibility.SHARED);
		CreateContactListResponse response = new CreateContactListResponse();

		CreateContactListCommand createContactListBean = mock(CreateContactListCommand.class);
		when(createContactListBean.executeCommand(options))
				.thenReturn(new ResponseContainer<CreateContactListResponse>(response));

		engage.setCreateContactListBean(createContactListBean);

		ResponseContainer<CreateContactListResponse> responseContainer = engage.createContactList(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), CreateContactListResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testDeleteList() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		DeleteListOptions options = new DeleteListOptions(1L);
		DeleteListResponse response = new DeleteListResponse();

		DeleteListCommand deleteListBean = mock(DeleteListCommand.class);
		when(deleteListBean.executeCommand(options)).thenReturn(new ResponseContainer<DeleteListResponse>(response));

		engage.setDeleteListBean(deleteListBean);

		ResponseContainer<DeleteListResponse> responseContainer = engage.deleteList(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), DeleteListResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testSelectRecipientData() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		SelectRecipientDataResponse response = new SelectRecipientDataResponse();
		SelectRecipientDataCommand selectRecipientDataBean = mock(SelectRecipientDataCommand.class);
		when(selectRecipientDataBean.executeCommand(options))
				.thenReturn(new ResponseContainer<SelectRecipientDataResponse>(response));

		engage.setSelectRecipientDataBean(selectRecipientDataBean);

		ResponseContainer<SelectRecipientDataResponse> responseContainer = engage.selectRecipientData(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), SelectRecipientDataResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testAddRecipient() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		AddRecipientOptions options = new AddRecipientOptions(1L);
		AddRecipientResponse response = new AddRecipientResponse();

		AddRecipientCommand addRecipientBean = mock(AddRecipientCommand.class);
		when(addRecipientBean.executeCommand(options))
				.thenReturn(new ResponseContainer<AddRecipientResponse>(response));

		engage.setAddRecipientBean(addRecipientBean);

		ResponseContainer<AddRecipientResponse> responseContainer = engage.addRecipient(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), AddRecipientResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testDoubleOptInRecipient() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		DoubleOptInRecipientOptions options = new DoubleOptInRecipientOptions(1L);
		DoubleOptInRecipientResponse response = new DoubleOptInRecipientResponse();

		DoubleOptInRecipientCommand doubleOptInRecipientBean = mock(DoubleOptInRecipientCommand.class);
		when(doubleOptInRecipientBean.executeCommand(options))
				.thenReturn(new ResponseContainer<DoubleOptInRecipientResponse>(response));

		engage.setDoubleOptInRecipientBean(doubleOptInRecipientBean);

		ResponseContainer<DoubleOptInRecipientResponse> responseContainer = engage.doubleOptInRecipient(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), DoubleOptInRecipientResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testUpdateRecipient() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		UpdateRecipientResponse response = new UpdateRecipientResponse();

		UpdateRecipientCommand updateRecipientBean = mock(UpdateRecipientCommand.class);
		when(updateRecipientBean.executeCommand(options))
				.thenReturn(new ResponseContainer<UpdateRecipientResponse>(response));

		engage.setUpdateRecipientBean(updateRecipientBean);

		ResponseContainer<UpdateRecipientResponse> responseContainer = engage.updateRecipient(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), UpdateRecipientResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testOptOutRecipient() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		OptOutRecipientOptions options = new OptOutRecipientOptions(1L);
		OptOutRecipientResponse response = new OptOutRecipientResponse();

		OptOutRecipientCommand optOutRecipientBean = mock(OptOutRecipientCommand.class);
		when(optOutRecipientBean.executeCommand(options))
				.thenReturn(new ResponseContainer<OptOutRecipientResponse>(response));

		engage.setOptOutRecipientBean(optOutRecipientBean);

		ResponseContainer<OptOutRecipientResponse> responseContainer = engage.optOutRecipient(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), OptOutRecipientResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testRemoveRecipient() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		RemoveRecipientOptions options = new RemoveRecipientOptions(1L);
		RemoveRecipientResponse response = new RemoveRecipientResponse();

		RemoveRecipientCommand removeRecipientBean = mock(RemoveRecipientCommand.class);
		when(removeRecipientBean.executeCommand(options))
				.thenReturn(new ResponseContainer<RemoveRecipientResponse>(response));

		engage.setRemoveRecipientBean(removeRecipientBean);

		ResponseContainer<RemoveRecipientResponse> responseContainer = engage.removeRecipient(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), RemoveRecipientResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testGetLists() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		GetListsOptions options = new GetListsOptions();
		GetListsResponse response = new GetListsResponse();

		GetListsCommand getListsBean = mock(GetListsCommand.class);
		when(getListsBean.executeCommand(options)).thenReturn(new ResponseContainer<GetListsResponse>(response));

		engage.setGetListsBean(getListsBean);

		ResponseContainer<GetListsResponse> responseContainer = engage.getLists(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), GetListsResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testCreateTable() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		CreateTableOptions options = new CreateTableOptions("test");
		CreateTableResponse response = new CreateTableResponse();

		CreateTableCommand createTableBean = mock(CreateTableCommand.class);
		when(createTableBean.executeCommand(options)).thenReturn(new ResponseContainer<CreateTableResponse>(response));

		engage.setCreateTableBean(createTableBean);

		ResponseContainer<CreateTableResponse> responseContainer = engage.createTable(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), CreateTableResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testJoinTable() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("test1", "test2");

		JoinTableOptions options = new JoinTableOptions(testMap);
		JoinTableResponse response = new JoinTableResponse();

		JoinTableCommand joinTableBean = mock(JoinTableCommand.class);
		when(joinTableBean.executeCommand(options)).thenReturn(new ResponseContainer<JoinTableResponse>(response));

		engage.setJoinTableBean(joinTableBean);

		ResponseContainer<JoinTableResponse> responseContainer = engage.joinTable(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), JoinTableResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testInsertUpdateRelationalTable() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		InsertUpdateRelationalTableOptions options = new InsertUpdateRelationalTableOptions(1L);
		InsertUpdateRelationalTableResponse response = new InsertUpdateRelationalTableResponse();

		InsertUpdateRelationalTableCommand insertUpdateRelationalTableBean = mock(
				InsertUpdateRelationalTableCommand.class);
		when(insertUpdateRelationalTableBean.executeCommand(options))
				.thenReturn(new ResponseContainer<InsertUpdateRelationalTableResponse>(response));

		engage.setInsertUpdateRelationalTableBean(insertUpdateRelationalTableBean);

		ResponseContainer<InsertUpdateRelationalTableResponse> responseContainer = engage
				.insertUpdateRelationalTable(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), InsertUpdateRelationalTableResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testDeleteRelationalTableData() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		DeleteRelationalTableDataOptions options = new DeleteRelationalTableDataOptions(1L);
		DeleteRelationalTableDataResponse response = new DeleteRelationalTableDataResponse();

		DeleteRelationalTableDataCommand deleteRelationalTableDataBean = mock(DeleteRelationalTableDataCommand.class);
		when(deleteRelationalTableDataBean.executeCommand(options))
				.thenReturn(new ResponseContainer<DeleteRelationalTableDataResponse>(response));

		engage.setDeleteRelationalTableDataBean(deleteRelationalTableDataBean);

		ResponseContainer<DeleteRelationalTableDataResponse> responseContainer = engage
				.deleteRelationalTableData(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), DeleteRelationalTableDataResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testPurgeTable() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		PurgeTableOptions options = new PurgeTableOptions();
		PurgeTableResponse response = new PurgeTableResponse();

		PurgeTableCommand purgeTableBean = mock(PurgeTableCommand.class);
		when(purgeTableBean.executeCommand(options)).thenReturn(new ResponseContainer<PurgeTableResponse>(response));

		engage.setPurgeTableBean(purgeTableBean);

		ResponseContainer<PurgeTableResponse> responseContainer = engage.purgeTable(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), PurgeTableResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testRawRecipientDataExport() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		RawRecipientDataExportOptions options = new RawRecipientDataExportOptions();
		RawRecipientDataExportResponse response = new RawRecipientDataExportResponse();

		RawRecipientDataExportCommand rawRecipientDataExportBean = mock(RawRecipientDataExportCommand.class);
		when(rawRecipientDataExportBean.executeCommand(options))
				.thenReturn(new ResponseContainer<RawRecipientDataExportResponse>(response));

		engage.setRawRecipientDataExportBean(rawRecipientDataExportBean);

		ResponseContainer<RawRecipientDataExportResponse> responseContainer = engage.rawRecipientDataExport(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), RawRecipientDataExportResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testAddListColumn() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		AddListColumnOptions options = new AddListColumnOptions(1L, "test column", ListColumnType.COUNTRY,
				"default value");
		AddListColumnResponse response = new AddListColumnResponse();

		AddListColumnCommand addListColumnBean = mock(AddListColumnCommand.class);
		when(addListColumnBean.executeCommand(options))
				.thenReturn(new ResponseContainer<AddListColumnResponse>(response));

		engage.setAddListColumnBean(addListColumnBean);

		ResponseContainer<AddListColumnResponse> responseContainer = engage.addListColumn(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), AddListColumnResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testSetColumnValue() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		SetColumnValueOptions options = new SetColumnValueOptions(1L, "test column", ColumnValueAction.UPDATE);
		SetColumnValueResponse response = new SetColumnValueResponse();

		SetColumnValueCommand setColumnValueBean = mock(SetColumnValueCommand.class);
		when(setColumnValueBean.executeCommand(options))
				.thenReturn(new ResponseContainer<SetColumnValueResponse>(response));

		engage.setSetColumnValueBean(setColumnValueBean);

		ResponseContainer<SetColumnValueResponse> responseContainer = engage.setColumnValue(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), SetColumnValueResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}

	@Test
	public void testScheduleMailing() {
		OAuthClient oAuthClient = mock(OAuthClient.class);
		SFTP sftp = mock(SFTP.class);
		Engage engage = new Engage(oAuthClient, sftp);

		ScheduleMailingOptions options = new ScheduleMailingOptions(1L, 2L, "test column");
		ScheduleMailingResponse response = new ScheduleMailingResponse();

		ScheduleMailingCommand scheduleMailingBean = mock(ScheduleMailingCommand.class);
		when(scheduleMailingBean.executeCommand(options))
				.thenReturn(new ResponseContainer<ScheduleMailingResponse>(response));

		engage.setScheduleMailingBean(scheduleMailingBean);

		ResponseContainer<ScheduleMailingResponse> responseContainer = engage.scheduleMailing(options);

		assertEquals(responseContainer.getClass(), ResponseContainer.class);
		assertEquals(responseContainer.getResposne().getClass(), ScheduleMailingResponse.class);
		assertEquals(responseContainer.getResposne(), response);
	}
}
