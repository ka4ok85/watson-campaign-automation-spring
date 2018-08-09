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

import com.github.ka4ok85.wca.command.AddRecipientCommand;
import com.github.ka4ok85.wca.command.CreateContactListCommand;
import com.github.ka4ok85.wca.command.CreateTableCommand;
import com.github.ka4ok85.wca.command.DeleteListCommand;
import com.github.ka4ok85.wca.command.DeleteRelationalTableDataCommand;
import com.github.ka4ok85.wca.command.DoubleOptInRecipientCommand;
import com.github.ka4ok85.wca.command.ExportListCommand;
import com.github.ka4ok85.wca.command.ExportTableCommand;
import com.github.ka4ok85.wca.command.GetFolderPathCommand;
import com.github.ka4ok85.wca.command.GetListsCommand;
import com.github.ka4ok85.wca.command.InsertUpdateRelationalTableCommand;
import com.github.ka4ok85.wca.command.JoinTableCommand;
import com.github.ka4ok85.wca.command.OptOutRecipientCommand;
import com.github.ka4ok85.wca.command.PurgeTableCommand;
import com.github.ka4ok85.wca.command.RemoveRecipientCommand;
import com.github.ka4ok85.wca.command.SelectRecipientDataCommand;
import com.github.ka4ok85.wca.command.UpdateRecipientCommand;
import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.GetFolderPathObjectType;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.AddRecipientOptions;
import com.github.ka4ok85.wca.options.CreateContactListOptions;
import com.github.ka4ok85.wca.options.CreateTableOptions;
import com.github.ka4ok85.wca.options.DeleteListOptions;
import com.github.ka4ok85.wca.options.DeleteRelationalTableDataOptions;
import com.github.ka4ok85.wca.options.DoubleOptInRecipientOptions;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.options.GetFolderPathOptions;
import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.options.JoinTableOptions;
import com.github.ka4ok85.wca.options.OptOutRecipientOptions;
import com.github.ka4ok85.wca.options.PurgeTableOptions;
import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.options.UpdateRecipientOptions;
import com.github.ka4ok85.wca.response.AddRecipientResponse;
import com.github.ka4ok85.wca.response.CreateContactListResponse;
import com.github.ka4ok85.wca.response.CreateTableResponse;
import com.github.ka4ok85.wca.response.DeleteListResponse;
import com.github.ka4ok85.wca.response.DeleteRelationalTableDataResponse;
import com.github.ka4ok85.wca.response.DoubleOptInRecipientResponse;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.response.GetFolderPathResponse;
import com.github.ka4ok85.wca.response.GetListsResponse;
import com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse;
import com.github.ka4ok85.wca.response.JoinTableResponse;
import com.github.ka4ok85.wca.response.OptOutRecipientResponse;
import com.github.ka4ok85.wca.response.PurgeTableResponse;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;
import com.github.ka4ok85.wca.response.UpdateRecipientResponse;
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

}
