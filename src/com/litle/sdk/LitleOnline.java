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
import javax.xml.bind.UnmarshalException;
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
import com.litle.sdk.generate.LitleOnlineRequest;
import com.litle.sdk.generate.LitleOnlineResponse;
import com.litle.sdk.generate.ObjectFactory;
import com.litle.sdk.generate.TransactionTypeWithReportGroup;
import com.litle.sdk.generate.TransactionTypeWithReportGroupAndPartial;

public class LitleOnline {
	
	private JAXBContext jc;
	private Properties config;
	private ObjectFactory objectFactory;
	
	public LitleOnline() {
		try {
			jc = JAXBContext.newInstance("com.litle.sdk.generate");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			config = new Properties();
			config.load(new FileInputStream(Configuration.location()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		objectFactory = new ObjectFactory();
	}

	//TODO This shouldn't throw "Exception"
	public AuthorizationResponse authorize(Authorization auth) throws Exception {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(auth);
		
		request.setTransaction(objectFactory.createAuthorization(auth));
		LitleOnlineResponse response = sendToLitle(request, AuthorizationResponse.class);
		JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
		return (AuthorizationResponse)newresponse.getValue();
	}
	
	private LitleOnlineResponse sendToLitle(LitleOnlineRequest request, Class<?> clazz) throws Exception {
		Marshaller m = jc.createMarshaller();
		Unmarshaller u = jc.createUnmarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(request, sw);
		String xmlRequest = sw.toString();
		
		String xmlResponse = new Communication().requestToServer(xmlRequest, config);
		try {
			LitleOnlineResponse response = (LitleOnlineResponse)u.unmarshal(new StringReader(xmlResponse));
			return response;
		} catch(UnmarshalException ume) {
			throw new LitleOnlineException("Error validating xml data against the schema", ume);
		}
		
	}

	public AuthReversalResponse authReversal(AuthReversal reversal) throws Exception {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(reversal);
		
		ObjectFactory o = new ObjectFactory();
		request.setTransaction(o.createAuthReversal(reversal));
		
		Marshaller m = jc.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(request, sw);
		String xmlRequest = sw.toString();
		
		String xmlResponse = new Communication().requestToServer(xmlRequest, config);
		Unmarshaller u = jc.createUnmarshaller();
		try {
			LitleOnlineResponse response = (LitleOnlineResponse)u.unmarshal(new StringReader(xmlResponse));
			JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
			return (AuthReversalResponse)newresponse.getValue();
		} catch(UnmarshalException ume) {
			AuthReversalResponse response = new AuthReversalResponse();
			response.setMessage("Error validating xml data against the schema: " + ume.getMessage());
			return response;
		}
	}
	
	public CaptureResponse capture(Capture capture) throws Exception {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(capture);
		
		ObjectFactory o = new ObjectFactory();
		request.setTransaction(o.createCapture(capture));
		
		Marshaller m = jc.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(request, sw);
		String xmlRequest = sw.toString();
		
		String xmlResponse = new Communication().requestToServer(xmlRequest, config);
		Unmarshaller u = jc.createUnmarshaller();
		try {
			LitleOnlineResponse response = (LitleOnlineResponse)u.unmarshal(new StringReader(xmlResponse));
			JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
			return (CaptureResponse)newresponse.getValue();
		} catch(UnmarshalException ume) {
			CaptureResponse response = new CaptureResponse();
			response.setMessage("Error validating xml data against the schema: " + ume.getMessage());
			return response;
		}
	}
	
	public CaptureGivenAuthResponse captureGivenAuth(CaptureGivenAuth captureGivenAuth) throws Exception {
		LitleOnlineRequest request = createLitleOnlineRequest();
		fillInReportGroup(captureGivenAuth);
		
		ObjectFactory o = new ObjectFactory();
		request.setTransaction(o.createCaptureGivenAuth(captureGivenAuth));
		
		Marshaller m = jc.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(request, sw);
		String xmlRequest = sw.toString();
		
		String xmlResponse = new Communication().requestToServer(xmlRequest, config);
		Unmarshaller u = jc.createUnmarshaller();
		try {
			LitleOnlineResponse response = (LitleOnlineResponse)u.unmarshal(new StringReader(xmlResponse));
			JAXBElement<? extends TransactionTypeWithReportGroup> newresponse = response.getTransactionResponse();
			return (CaptureGivenAuthResponse)newresponse.getValue();
		} catch(UnmarshalException ume) {
			CaptureGivenAuthResponse response = new CaptureGivenAuthResponse();
			response.setMessage("Error validating xml data against the schema: " + ume.getMessage());
			return response;
		}
	}

	private LitleOnlineRequest createLitleOnlineRequest() {
		LitleOnlineRequest request = new LitleOnlineRequest();
		request.setMerchantId(config.getProperty("merchantId"));
		request.setVersion(config.getProperty("version"));
		Authentication authentication = new Authentication();
		authentication.setPassword(config.getProperty("password"));
		authentication.setUser(config.getProperty("username"));
		request.setAuthentication(authentication);
		return request;
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
