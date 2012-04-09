package com.litle.sdk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.litle.sdk.generate.AuthReversal;
import com.litle.sdk.generate.AuthReversalResponse;
import com.litle.sdk.generate.Authentication;
import com.litle.sdk.generate.Authorization;
import com.litle.sdk.generate.AuthorizationResponse;
import com.litle.sdk.generate.Capture;
import com.litle.sdk.generate.CaptureGivenAuth;
import com.litle.sdk.generate.CaptureGivenAuthResponse;
import com.litle.sdk.generate.CaptureResponse;
import com.litle.sdk.generate.Credit;
import com.litle.sdk.generate.CreditResponse;
import com.litle.sdk.generate.EcheckCredit;
import com.litle.sdk.generate.EcheckCreditResponse;
import com.litle.sdk.generate.EcheckRedeposit;
import com.litle.sdk.generate.EcheckRedepositResponse;
import com.litle.sdk.generate.EcheckSale;
import com.litle.sdk.generate.EcheckSalesResponse;
import com.litle.sdk.generate.EcheckVerification;
import com.litle.sdk.generate.EcheckVerificationResponse;
import com.litle.sdk.generate.ForceCapture;
import com.litle.sdk.generate.ForceCaptureResponse;
import com.litle.sdk.generate.LitleOnlineRequest;
import com.litle.sdk.generate.LitleOnlineResponse;
import com.litle.sdk.generate.ObjectFactory;
import com.litle.sdk.generate.RegisterTokenRequestType;
import com.litle.sdk.generate.RegisterTokenResponse;
import com.litle.sdk.generate.Sale;
import com.litle.sdk.generate.SaleResponse;
import com.litle.sdk.generate.TransactionTypeWithReportGroup;
import com.litle.sdk.generate.TransactionTypeWithReportGroupAndPartial;
import com.litle.sdk.generate.VoidResponse;

public class LitleOnline {
	
	private JAXBContext jc;
	private Properties config;
	private ObjectFactory objectFactory;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private Communication communication;
	
	/**
	 * Construct a LitleOnline using the configuration specified in $HOME/.litle_SDK_config.properties
	 */
	public LitleOnline() {
		try {
			jc = JAXBContext.newInstance("com.litle.sdk.generate");
			marshaller = jc.createMarshaller();
			unmarshaller = jc.createUnmarshaller();
			communication = new Communication();
			objectFactory = new ObjectFactory();
		} catch (JAXBException e) {
			throw new LitleOnlineException("Unable to load jaxb dependencies.  Perhaps a classpath issue?", e);
		}
		try {
			config = new Properties();
			config.load(new FileInputStream(Configuration.location()));
		} catch (FileNotFoundException e) {
			throw new LitleOnlineException("Configuration file not found. If you are not using the .litle_SDK_config.properties file, please use the LitleOnline(Properties) constructor.  If you are using .litle_SDK_config.properties, you can generate one using java -jar litle-sdk-for-java-8.10.jar", e);
		} catch (IOException e) {
			throw new LitleOnlineException("Configuration file could not be loaded.  Check to see if the user running this has permission to access the file", e);
		}
	}
	
	/**
	 * Construct a LitleOnline specifying the configuration in code.  This should be used by integrations that
	 * have another way to specify their configuration settings (ofbiz, etc)
	 * 
	 * Properties that *must* be set are:
	 * 
	 * 	url (eg https://payments.litle.com/vap/communicator/online)
	 *	reportGroup (eg "Default Report Group")
	 *	username
	 *	merchantId
	 *	password
	 *	version (eg 8.10)
	 *	timeout (in seconds)
	 *	Optional properties are: 
	 *	proxyHost
	 *	proxyPort
	 *	printxml (possible values "true" and "false" - defaults to false)
	 *
	 * @param config
	 */
	public LitleOnline(Properties config) {
		this.config = config;
		try {
			jc = JAXBContext.newInstance("com.litle.sdk.generate");
			marshaller = jc.createMarshaller();
			unmarshaller = jc.createUnmarshaller();
			communication = new Communication();
			objectFactory = new ObjectFactory();
		} catch (JAXBException e) {
			throw new LitleOnlineException("Unable to load jaxb dependencies.  Perhaps a classpath issue?", e);
		}
	}

	protected void setCommunication(Communication communication) {
		this.communication = communication;
	}

	/**
	 * <script src="https://gist.github.com/2139120.js"></script>
	 */
	public AuthorizationResponse authorize(Authorization auth) throws LitleOnlineException {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(auth);
		
		request.setTransaction(auth);
		LitleOnlineResponse response = sendToLitle(request);
		return (AuthorizationResponse)response.getTransactionResponse();
	}

	/**
	 * <script src="https://gist.github.com/2174863.js"></script>
	 */
	public AuthReversalResponse authReversal(AuthReversal reversal) throws LitleOnlineException {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(reversal);
		
		request.setTransaction(reversal);
		LitleOnlineResponse response = sendToLitle(request);
		return (AuthReversalResponse)response.getTransactionResponse();
	}
	
	/**
	 * <script src="https://gist.github.com/2139736.js"></script>
	 */
	public CaptureResponse capture(Capture capture) throws LitleOnlineException {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(capture);
		
		request.setTransaction(capture);
		LitleOnlineResponse response = sendToLitle(request);
		return (CaptureResponse)response.getTransactionResponse();
	}
	
	/**
	 * <script src="https://gist.github.com/2139803.js"></script>
	 */
	public CaptureGivenAuthResponse captureGivenAuth(CaptureGivenAuth captureGivenAuth) throws LitleOnlineException {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(captureGivenAuth);
		
		request.setTransaction(captureGivenAuth);
		LitleOnlineResponse response = sendToLitle(request);
		return (CaptureGivenAuthResponse)response.getTransactionResponse();
	}

//	/**
//	 * <script src="https://gist.github.com/2139739.js"></script>
//	 */
//	public CreditResponse credit(Credit credit) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(credit);
//		
//		request.setTransaction(objectFactory.createCredit(credit));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (CreditResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2139831.js"></script>
//	 */
//	public EcheckCreditResponse echeckCredit(EcheckCredit echeckcredit) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(echeckcredit);
//		
//		request.setTransaction(objectFactory.createEcheckCredit(echeckcredit));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (EcheckCreditResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2139852.js"></script>
//	 */
//	public EcheckRedepositResponse echeckRedeposit(EcheckRedeposit echeckRedeposit) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(echeckRedeposit);
//		
//		request.setTransaction(objectFactory.createEcheckRedeposit(echeckRedeposit));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (EcheckRedepositResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2139856.js"></script>
//	 */
//	public EcheckSalesResponse echeckSale(EcheckSale echeckSale) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(echeckSale);
//		
//		request.setTransaction(objectFactory.createEcheckSale(echeckSale));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (EcheckSalesResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2139863.js"></script>
//	 */
//	public EcheckVerificationResponse echeckVerification(EcheckVerification echeckVerification) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(echeckVerification);
//		
//		request.setTransaction(objectFactory.createEcheckVerification(echeckVerification));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (EcheckVerificationResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2174943.js"></script>
//	 */
//	public ForceCaptureResponse forceCapture(ForceCapture forceCapture) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(forceCapture);
//		
//		request.setTransaction(objectFactory.createForceCapture(forceCapture));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (ForceCaptureResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2139304.js"></script>
//	 */
//	public SaleResponse sale(Sale sale) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(sale);
//		
//		request.setTransaction(objectFactory.createSale(sale));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (SaleResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2139877.js"></script>
//	 */
//	public RegisterTokenResponse registerToken(RegisterTokenRequestType tokenRequest) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(tokenRequest);
//		
//		request.setTransaction(objectFactory.createRegisterTokenRequest(tokenRequest));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (RegisterTokenResponse)newresponse.getValue();
//	}
//	
//	/**
//	 * <script src="https://gist.github.com/2139880.js"></script>
//	 */
//	public VoidResponse dovoid(com.litle.sdk.generate.Void v) throws LitleOnlineException {
//		LitleOnlineRequest request = createLitleOnlineRequest();
//		fillInReportGroup(v);
//		
//		request.setTransaction(objectFactory.createVoid(v));
//		LitleOnlineResponse response = sendToLitle(request);
//		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
//		return (VoidResponse)newresponse.getValue();
//	}
//
	private LitleOnlineRequest createLitleOnlineRequest() {
		try {
			LitleOnlineRequest request = objectFactory.createLitleOnlineRequest();
			request.setMerchantId(config.getProperty("merchantId"));
			request.setVersion(config.getProperty("version"));
			Authentication authentication = objectFactory.createAuthentication();
			authentication.setPassword(config.getProperty("password"));
			authentication.setUser(config.getProperty("username"));
			request.setAuthentication(authentication);
			return request;
		} catch (JAXBException e) {
			throw new LitleOnlineException("Error constructing basic objects", e);
		}
	}
	
	private LitleOnlineResponse sendToLitle(LitleOnlineRequest request) throws LitleOnlineException {
		try {
			StringWriter sw = new StringWriter();
			marshaller.marshal(request, sw);
			String xmlRequest = sw.toString();
			
			String xmlResponse = communication.requestToServer(xmlRequest, config);
			LitleOnlineResponse response = (LitleOnlineResponse)unmarshaller.unmarshal(new StringReader(xmlResponse));
			if("1".equals(response.getResponse())) {
				throw new LitleOnlineException(response.getMessage());
			}
			return response;
		} catch(JAXBException ume) {
			throw new LitleOnlineException("Error validating xml data against the schema", ume);
		}
	}

	private void fillInReportGroup(TransactionTypeWithReportGroup txn) {
		if(txn.getReportGroup() == null) {
			txn.setReportGroup(config.getProperty("reportGroup")); 
		}
	}
	
	private void fillInReportGroup(TransactionTypeWithReportGroupAndPartial txn) {
		if(txn.getReportGroup() == null) {
			txn.setReportGroup(config.getProperty("reportGroup")); 
		}
	}
}
