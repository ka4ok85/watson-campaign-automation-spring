package com.github.ka4ok85.wca;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ka4ok85.wca.command.AddRecipientCommand;
import com.github.ka4ok85.wca.command.CreateContactListCommand;
import com.github.ka4ok85.wca.command.DeleteListCommand;
import com.github.ka4ok85.wca.command.DoubleOptInRecipientCommand;
import com.github.ka4ok85.wca.command.ExportListCommand;
import com.github.ka4ok85.wca.command.ExportTableCommand;
import com.github.ka4ok85.wca.command.GetFolderPathCommand;
import com.github.ka4ok85.wca.command.SelectRecipientDataCommand;
import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.GetFolderPathObjectType;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.AddRecipientOptions;
import com.github.ka4ok85.wca.options.CreateContactListOptions;
import com.github.ka4ok85.wca.options.DeleteListOptions;
import com.github.ka4ok85.wca.options.DoubleOptInRecipientOptions;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.options.GetFolderPathOptions;
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.response.AddRecipientResponse;
import com.github.ka4ok85.wca.response.CreateContactListResponse;
import com.github.ka4ok85.wca.response.DeleteListResponse;
import com.github.ka4ok85.wca.response.DoubleOptInRecipientResponse;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.response.GetFolderPathResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;
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

}
