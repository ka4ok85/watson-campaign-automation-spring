package com.github.ka4ok85.wca;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.options.AddRecipientOptions;
import com.github.ka4ok85.wca.options.CalculateQueryOptions;
import com.github.ka4ok85.wca.options.CreateContactListOptions;
import com.github.ka4ok85.wca.options.CreateTableOptions;
import com.github.ka4ok85.wca.options.DeleteListOptions;
import com.github.ka4ok85.wca.options.DeleteRelationalTableDataOptions;
import com.github.ka4ok85.wca.options.DeleteTableOptions;
import com.github.ka4ok85.wca.options.DoubleOptInRecipientOptions;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.options.JoinTableOptions;
import com.github.ka4ok85.wca.options.OptOutRecipientOptions;
import com.github.ka4ok85.wca.options.PurgeTableOptions;
import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.options.UpdateRecipientOptions;
import com.github.ka4ok85.wca.pod.Pod;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;
import com.github.ka4ok85.wca.response.UpdateRecipientResponse;
import com.github.ka4ok85.wca.sftp.SFTP;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.AddRecipientResponse;
import com.github.ka4ok85.wca.response.CalculateQueryResponse;
import com.github.ka4ok85.wca.response.CreateContactListResponse;
import com.github.ka4ok85.wca.response.CreateTableResponse;
import com.github.ka4ok85.wca.response.DeleteListResponse;
import com.github.ka4ok85.wca.response.DeleteRelationalTableDataResponse;
import com.github.ka4ok85.wca.response.DeleteTableResponse;
import com.github.ka4ok85.wca.response.DoubleOptInRecipientResponse;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.response.GetListsResponse;
import com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse;
import com.github.ka4ok85.wca.response.JoinTableResponse;
import com.github.ka4ok85.wca.response.OptOutRecipientResponse;
import com.github.ka4ok85.wca.response.PurgeTableResponse;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;
import com.github.ka4ok85.wca.command.AbstractCommand;
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
import com.github.ka4ok85.wca.command.GetListsCommand;
import com.github.ka4ok85.wca.command.InsertUpdateRelationalTableCommand;
import com.github.ka4ok85.wca.command.JoinTableCommand;
import com.github.ka4ok85.wca.command.SelectRecipientDataCommand;
import com.github.ka4ok85.wca.command.UpdateRecipientCommand;
import com.github.ka4ok85.wca.command.OptOutRecipientCommand;
import com.github.ka4ok85.wca.command.PurgeTableCommand;
import com.github.ka4ok85.wca.command.RemoveRecipientCommand;
import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.oauth.OAuthClientImplementation;

//@EnableRetry
//@Service
//@SpringBootApplication
public class Engage {
	final String grantType = "refresh_token";

	private int podNumber;
	private String clientId;
	private String clientSecret;
	private String refreshToken;
	private String accessUrl;
	private String accessToken;
	private OAuthClient oAuthClient;
	private SFTP sftp;

	private static AnnotationConfigApplicationContext applicationContext;
	
	private static final Logger log = LoggerFactory.getLogger(Engage.class);

	public Engage(int podNumber, String clientId, String clientSecret, String refreshToken) {
		this.podNumber = podNumber;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
		this.oAuthClient = new OAuthClientImplementation(podNumber, clientId, clientSecret, refreshToken);
		this.sftp = new SFTP(this.oAuthClient);
	}

/*
	public ResponseContainer<AbstractResponse> run(Class<?> clazz, AbstractOptions options) throws FailedGetAccessTokenException {
			OAuthClient oAuthClient = new OAuthClientImplementation(podNumber, clientId, clientSecret, refreshToken);
			accessToken = oAuthClient.getAccessToken();
			String name = clazz.getName();
			Constructor<?> constructor;
			try {
				constructor = clazz.getConstructor();
				Class<? extends Constructor> x = constructor.getClass();
				System.out.println("Q:             " + x);
				AbstractCommand<AbstractResponse> command = (AbstractCommand<AbstractResponse>) constructor.newInstance();
				command.setoAuthClient(oAuthClient);
				ResponseContainer<AbstractResponse> response = command.executeCommand(options);
				System.out.println("-------------output response----------");
				System.out.println(response.getResposne());
				
				return response;

			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return null;
	}
	*/
	

	public ResponseContainer<ExportListResponse> exportList(ExportListOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		//ExportListCommand command = new ExportListCommand(this.oAuthClient, this.sftp); 
		//ResponseContainer<ExportListResponse> result = command.executeCommand(options);
		
		
		//System.out.println("exportList" + exportList);
		
		ExportListCommand exportList = getApplicationContext().getBean(ExportListCommand.class);
		
		System.out.println("exportList" + exportList);
		
		exportList.setoAuthClient(oAuthClient);
		exportList.setSftp(sftp);
		ResponseContainer<ExportListResponse> result = exportList.executeCommand(options);

		return result;
	}

	public ResponseContainer<ExportTableResponse> exportTable(ExportTableOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		ExportTableCommand exportTable = getApplicationContext().getBean(ExportTableCommand.class);
		
		System.out.println("exportTable" + exportTable);
		
		exportTable.setoAuthClient(oAuthClient);
		exportTable.setSftp(sftp);
		ResponseContainer<ExportTableResponse> result = exportTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<CreateContactListResponse> createContactList(CreateContactListOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		CreateContactListCommand createContactList = getApplicationContext().getBean(CreateContactListCommand.class);
		
		System.out.println("createContactList: " + createContactList);
		
		createContactList.setoAuthClient(oAuthClient);
		//createContactList.setSftp(sftp);
		ResponseContainer<CreateContactListResponse> result = createContactList.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteListResponse> deleteList(DeleteListOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DeleteListCommand deleteList = getApplicationContext().getBean(DeleteListCommand.class);
		
		System.out.println("deleteListOptions: " + deleteList);
		
		deleteList.setoAuthClient(oAuthClient);
		//createContactList.setSftp(sftp);
		ResponseContainer<DeleteListResponse> result = deleteList.executeCommand(options);

		return result;
	}

	public ResponseContainer<SelectRecipientDataResponse> selectRecipientData(SelectRecipientDataOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		SelectRecipientDataCommand selectRecipientData = getApplicationContext().getBean(SelectRecipientDataCommand.class);

		selectRecipientData.setoAuthClient(oAuthClient);
		selectRecipientData.setSftp(sftp);
		ResponseContainer<SelectRecipientDataResponse> result = selectRecipientData.executeCommand(options);

		return result;
	}
	
	public ResponseContainer<AddRecipientResponse> addRecipient(AddRecipientOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		AddRecipientCommand addRecipient = getApplicationContext().getBean(AddRecipientCommand.class);

		addRecipient.setoAuthClient(oAuthClient);
		addRecipient.setSftp(sftp);
		ResponseContainer<AddRecipientResponse> result = addRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<DoubleOptInRecipientResponse> doubleOptInRecipient(DoubleOptInRecipientOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DoubleOptInRecipientCommand doubleOptInRecipient = getApplicationContext().getBean(DoubleOptInRecipientCommand.class);

		doubleOptInRecipient.setoAuthClient(oAuthClient);
		doubleOptInRecipient.setSftp(sftp);
		ResponseContainer<DoubleOptInRecipientResponse> result = doubleOptInRecipient.executeCommand(options);

		return result;
	}
	
	public ResponseContainer<UpdateRecipientResponse> updateRecipient(UpdateRecipientOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		UpdateRecipientCommand updateRecipient = getApplicationContext().getBean(UpdateRecipientCommand.class);

		updateRecipient.setoAuthClient(oAuthClient);
		updateRecipient.setSftp(sftp);
		ResponseContainer<UpdateRecipientResponse> result = updateRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<OptOutRecipientResponse> optOutRecipient(OptOutRecipientOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		OptOutRecipientCommand optOutRecipient = getApplicationContext().getBean(OptOutRecipientCommand.class);

		optOutRecipient.setoAuthClient(oAuthClient);
		optOutRecipient.setSftp(sftp);
		ResponseContainer<OptOutRecipientResponse> result = optOutRecipient.executeCommand(options);

		return result;
	}

	public ResponseContainer<RemoveRecipientResponse> removeRecipient(RemoveRecipientOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		RemoveRecipientCommand removeRecipient = getApplicationContext().getBean(RemoveRecipientCommand.class);

		removeRecipient.setoAuthClient(oAuthClient);
		removeRecipient.setSftp(sftp);
		ResponseContainer<RemoveRecipientResponse> result = removeRecipient.executeCommand(options);

		return result;
	}
	
	public ResponseContainer<GetListsResponse> getLists(GetListsOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		GetListsCommand getLists = getApplicationContext().getBean(GetListsCommand.class);

		getLists.setoAuthClient(oAuthClient);
		getLists.setSftp(sftp);
		ResponseContainer<GetListsResponse> result = getLists.executeCommand(options);

		return result;
	}

	public ResponseContainer<CreateTableResponse> createTable(CreateTableOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		CreateTableCommand createTable = getApplicationContext().getBean(CreateTableCommand.class);

		createTable.setoAuthClient(oAuthClient);
		createTable.setSftp(sftp);
		ResponseContainer<CreateTableResponse> result = createTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<JoinTableResponse> joinTable(JoinTableOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		JoinTableCommand joinTable = getApplicationContext().getBean(JoinTableCommand.class);

		joinTable.setoAuthClient(oAuthClient);
		joinTable.setSftp(sftp);
		ResponseContainer<JoinTableResponse> result = joinTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<InsertUpdateRelationalTableResponse> insertUpdateRelationalTable(InsertUpdateRelationalTableOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		InsertUpdateRelationalTableCommand insertUpdateRelationalTable = getApplicationContext().getBean(InsertUpdateRelationalTableCommand.class);

		insertUpdateRelationalTable.setoAuthClient(oAuthClient);
		insertUpdateRelationalTable.setSftp(sftp);
		ResponseContainer<InsertUpdateRelationalTableResponse> result = insertUpdateRelationalTable.executeCommand(options);

		return result;
	}
	
	public ResponseContainer<DeleteRelationalTableDataResponse> deleteRelationalTableData(DeleteRelationalTableDataOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DeleteRelationalTableDataCommand deleteRelationalTableData = getApplicationContext().getBean(DeleteRelationalTableDataCommand.class);

		deleteRelationalTableData.setoAuthClient(oAuthClient);
		deleteRelationalTableData.setSftp(sftp);
		ResponseContainer<DeleteRelationalTableDataResponse> result = deleteRelationalTableData.executeCommand(options);

		return result;
	}

	public ResponseContainer<PurgeTableResponse> purgeTable(PurgeTableOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		PurgeTableCommand purgeTable = getApplicationContext().getBean(PurgeTableCommand.class);

		purgeTable.setoAuthClient(oAuthClient);
		purgeTable.setSftp(sftp);
		ResponseContainer<PurgeTableResponse> result = purgeTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<DeleteTableResponse> deleteTable(DeleteTableOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		DeleteTableCommand deleteTable = getApplicationContext().getBean(DeleteTableCommand.class);

		deleteTable.setoAuthClient(oAuthClient);
		deleteTable.setSftp(sftp);
		ResponseContainer<DeleteTableResponse> result = deleteTable.executeCommand(options);

		return result;
	}

	public ResponseContainer<CalculateQueryResponse> calculateQuery(CalculateQueryOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		CalculateQueryCommand calculateQuery = getApplicationContext().getBean(CalculateQueryCommand.class);

		calculateQuery.setoAuthClient(oAuthClient);
		calculateQuery.setSftp(sftp);
		ResponseContainer<CalculateQueryResponse> result = calculateQuery.executeCommand(options);

		return result;
	}

	private static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
System.out.println("----applicationContext is null");
        	//applicationContext = new AnnotationConfigApplicationContext(SpringContext.class);
        	applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
System.out.println("----call refresh!");
        	//applicationContext.refresh();
        	//applicationContext.refresh();
        }
        return applicationContext;
    }

    /*
    public static EngageService getService(int podNumber, String clientId, String clientSecret, String refreshToken) {
System.out.println("----before getApplicationContext().getBean(EngageService.class)");
    	EngageService engageService = getApplicationContext().getBean(EngageService.class);
    	engageService.init(podNumber, clientId, clientSecret, refreshToken);
		//Engage engage = new Engage(podNumber, clientId, clientSecret, refreshToken);
System.out.println("----after getApplicationContext().getBean(EngageService.class)");
    	//engage.setClientId(clientId);
    	//engage.setClientSecret(clientSecret);
    	//engage.setPodNumber(podNumber);
    	//engage.setRefreshToken(refreshToken);
    	
    	
    	return engageService;
    }
*/

    
}
