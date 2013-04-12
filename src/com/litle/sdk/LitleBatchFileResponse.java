package com.litle.sdk;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.litle.sdk.generate.BatchResponse;
import com.litle.sdk.generate.TransactionType;
import com.litle.sdk.generate.TransactionTypeWithReportGroup;

import com.litle.sdk.generate.LitleResponse;


public class LitleBatchFileResponse {
	private JAXBContext jc;
	private LitleResponse litleResponse;
	private List<LitleBatchResponse> litleBatchResponseList;
	private Unmarshaller unmarshaller;
	private File xmlFile;
	
	public LitleBatchFileResponse(File xmlFile){
		// convert from xml to objects
		this.litleResponse = new LitleResponse();
		this.litleBatchResponseList = new ArrayList<LitleBatchResponse>();
		this.xmlFile = xmlFile;
	}
	
	public LitleBatchFileResponse(String xmlResponse) throws JAXBException {
		try {
			jc = JAXBContext.newInstance("com.litle.sdk.generate");
			this.unmarshaller = jc.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.litleResponse = new LitleResponse();
		this.litleBatchResponseList = new ArrayList<LitleBatchResponse>();
		this.litleResponse = (LitleResponse)unmarshaller.unmarshal(new StringReader(xmlResponse));
	}
	
	public List<LitleBatchResponse> getBatchResponseList(){
		return this.litleBatchResponseList;
	}
	
	public File getFile(){
		return xmlFile;
	}

	
	public void getTransaction() {
		
		for(BatchResponse batchResponse: litleResponse.getBatchResponses()) {
//			for(TransactionType lbr: (TransactionType) batchResponse.getTransactionResponses()) {
//				lbr.setBatchResponse(batchResponse);
//				lbr.setBatchResponse(lbr.getBatchResponse());
//			}
		}
	}
}
