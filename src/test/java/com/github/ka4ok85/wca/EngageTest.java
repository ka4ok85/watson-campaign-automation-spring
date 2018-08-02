package com.github.ka4ok85.wca;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ka4ok85.wca.command.GetFolderPathCommand;
import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.GetFolderPathObjectType;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.GetFolderPathOptions;
import com.github.ka4ok85.wca.response.GetFolderPathResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
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

}
