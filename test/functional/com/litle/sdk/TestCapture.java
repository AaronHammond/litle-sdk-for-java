package com.litle.sdk;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import com.litle.sdk.generate.Capture;
import com.litle.sdk.generate.CaptureResponse;
import com.litle.sdk.generate.EnhancedData;

public class TestCapture {

	@Test
	public void simpleCapture() throws Exception{
		Capture capture = new Capture();
		capture.setLitleTxnId(123456000);
		capture.setAmount(BigInteger.valueOf(106L));
		capture.setPayPalNotes("Notes");
		
		CaptureResponse response = new LitleOnline().capture(capture);
		assertEquals("Approved", response.getMessage());
	}
	
	@Test
	public void simpleCaptureWithPartial() throws Exception{
		Capture capture = new Capture();
		capture.setLitleTxnId(123456000);
		capture.setAmount(BigInteger.valueOf(106L));
		capture.setPartial(true);
		capture.setPayPalNotes("Notes");
		
		CaptureResponse response = new LitleOnline().capture(capture);
		assertEquals("Approved", response.getMessage());
	}
	
	@Test
	public void complexCapture() throws Exception{
		Capture capture = new Capture();
		capture.setLitleTxnId(123456000);
		capture.setAmount(BigInteger.valueOf(106L));
		capture.setPayPalNotes("Notes");
		EnhancedData enhancedData = new EnhancedData();
		enhancedData.setCustomerReference("Litle");
		enhancedData.setSalesTax(BigInteger.valueOf(50L));
		enhancedData.setDeliveryType("TBD");
		capture.setEnhancedData(enhancedData);
		capture.setPayPalOrderComplete(true);
		
		CaptureResponse response = new LitleOnline().capture(capture);
		assertEquals("Approved", response.getMessage());
	}


}


