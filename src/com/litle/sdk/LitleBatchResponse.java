package com.litle.sdk;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.litle.sdk.generate.BatchResponse;
import com.litle.sdk.generate.TransactionTypeWithReportGroup;

/**
 * Wrapper class to initialize the batch Responses
 */
public class LitleBatchResponse {
	private BatchResponse batchResponse;
	
	LitleBatchResponse(BatchResponse batchResponse) {
		setBatchResponse(batchResponse);
	}
	
	void setBatchResponse(BatchResponse batchResponse) {
		this.batchResponse = batchResponse;
	}
	
	BatchResponse getBatchResponse() {
		return this.batchResponse;
	}
	
	public long getLitleBatchId() {
		return this.batchResponse.getLitleBatchId();
	}
	
	public String getMerchantId() {
		return this.batchResponse.getMerchantId();
	}
	
//	public int getNumberOfTransactions(){
//		return this.batchResponse.getTransactionResponses().size();
//	}
	
	public TransactionTypeIterator getTransactionResponses(){
		return new TransactionTypeIterator(batchResponse.getTransactionResponses());
	}
	
	/** 
	 * This sub class is helps the user to navigate through the objects to access the values of the
	 * transaction responses.
	 * This class also provides the iterator to navigate through the objects.
	 *
	 */
	public static class TransactionTypeIterator implements Iterator<TransactionTypeWithReportGroup> {

		private Iterator<JAXBElement<? extends TransactionTypeWithReportGroup>> baseIterator;
		
		TransactionTypeIterator(List<JAXBElement<? extends TransactionTypeWithReportGroup>> baseList){
			baseIterator = baseList.iterator();
		}
		
		public boolean hasNext() {
			return baseIterator.hasNext();
		}

		public TransactionTypeWithReportGroup next() {
			return (TransactionTypeWithReportGroup) baseIterator.next().getValue();
		}

		public void remove() {
			baseIterator.remove();
		}
	}
}
