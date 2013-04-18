package com.litle.sdk;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.litle.sdk.generate.BatchResponse;

import com.litle.sdk.generate.LitleResponse;


public class LitleBatchFileResponse {
	private JAXBContext jc;
	private LitleResponse litleResponse;
	private List<LitleBatchResponse> litleBatchResponseList;
	private Unmarshaller unmarshaller;
	private File xmlFile;
	
	public LitleBatchFileResponse(File xmlFile) throws JAXBException{
		// convert from xml to objects
		
		try {
			this.xmlFile = xmlFile;
			jc = JAXBContext.newInstance("com.litle.sdk.generate");
			this.unmarshaller = jc.createUnmarshaller();
			this.litleResponse = (LitleResponse) unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			e.printStackTrace(); // TODO - convert to LitleBatchException
		}
		
		wrapBatchResponses(litleResponse.getBatchResponses());
	}
	
	public LitleBatchFileResponse(String xmlResponse) throws JAXBException {
		try {
			jc = JAXBContext.newInstance("com.litle.sdk.generate");
			this.unmarshaller = jc.createUnmarshaller();
			this.litleResponse = (LitleResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
		} catch (JAXBException e) {
			e.printStackTrace(); // TODO - convert to LitleBatchException
		}
		
		wrapBatchResponses(litleResponse.getBatchResponses());
	}
	
	private void wrapBatchResponses(List<BatchResponse> batchResponses) {
		litleBatchResponseList = new ArrayList<LitleBatchResponse>();
		for(BatchResponse br : batchResponses){
			LitleBatchResponse lbr = new LitleBatchResponse(br);
			litleBatchResponseList.add(lbr);
		}
	}
	
	public List<LitleBatchResponse> getBatchResponseList(){
		return this.litleBatchResponseList;
	}
	
	public File getFile() {
		return xmlFile;
	}
}
