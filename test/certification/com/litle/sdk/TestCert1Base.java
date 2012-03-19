package com.litle.sdk;


import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.BeforeClass;
import org.junit.Test;

import com.litle.sdk.generate.Authorization;
import com.litle.sdk.generate.AuthorizationResponse;
import com.litle.sdk.generate.Capture;
import com.litle.sdk.generate.CaptureResponse;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.Contact;
import com.litle.sdk.generate.CountryTypeEnum;
import com.litle.sdk.generate.Credit;
import com.litle.sdk.generate.CreditResponse;
import com.litle.sdk.generate.FraudCheckType;
import com.litle.sdk.generate.Sale;
import com.litle.sdk.generate.SaleResponse;
import com.litle.sdk.generate.VoidResponse;

public class TestCert1Base {

	private static LitleOnline litle;

	@BeforeClass
	public static void beforeClass() throws Exception {
		litle = new LitleOnline();
	}
	
	@Test
	public void test1Auth() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("1");
		authorization.setAmount(10010L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("John Smith");
		contact.setAddressLine1("1 Main St.");
		contact.setCity("Burlington");
		contact.setState("MA");
		contact.setZip("01803-3747");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4457010000000009");
		card.setExpDate("0112");
		card.setCardValidationNum("349");
		authorization.setCard(card);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "11111 ",response.getAuthCode());
		assertEquals(response.getMessage(), "01",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
		Capture capture = new Capture();
		capture.setLitleTxnId(response.getLitleTxnId());
		CaptureResponse captureresponse = litle.capture(capture);
		assertEquals(captureresponse.getMessage(), "000",captureresponse.getResponse());
		assertEquals(captureresponse.getMessage(), "Approved",captureresponse.getMessage());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(captureresponse.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test1AVS() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("1");
		authorization.setAmount(000L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("John Smith");
		contact.setAddressLine1("1 Main St.");
		contact.setCity("Burlington");
		contact.setState("MA");
		contact.setZip("01803-3747");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4457010000000009");
		card.setExpDate("0112");
		card.setCardValidationNum("349");
		authorization.setCard(card);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "11111 ",response.getAuthCode());
		assertEquals(response.getMessage(), "01",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
	}
	
	@Test
	public void test1Sale() throws Exception {
		Sale sale = new Sale();
		sale.setOrderId("1");
		sale.setAmount(10010L);
		sale.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("John Smith");
		contact.setAddressLine1("1 Main St.");
		contact.setCity("Burlington");
		contact.setState("MA");
		contact.setZip("01803-3747");
		contact.setCountry(CountryTypeEnum.US);
		sale.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4457010000000009");
		card.setExpDate("0112");
		card.setCardValidationNum("349");
		sale.setCard(card);
		
		SaleResponse response = litle.sale(sale);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "11111 ",response.getAuthCode());
		assertEquals(response.getMessage(), "01",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(response.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test2Auth() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("2");
		authorization.setAmount(20020L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Mike J. Hammer");
		contact.setAddressLine1("2 Main St.");
		contact.setAddressLine2("Apt. 222");
		contact.setCity("Riverside");
		contact.setState("RI");
		contact.setZip("02915");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("MC");
		card.setNumber("5112010000000003");
		card.setExpDate("0212");
		card.setCardValidationNum("261");
		authorization.setCard(card);
		FraudCheckType authenticationvalue = new FraudCheckType();
		authenticationvalue.setAuthenticationValue("BwABBJQ1AgAAAAAgJDUCAAAAAAA=");
		authorization.setCardholderAuthentication(authenticationvalue);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "22222",response.getAuthCode());
		assertEquals(response.getMessage(), "10",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
		Capture capture = new Capture();
		capture.setLitleTxnId(response.getLitleTxnId());
		CaptureResponse captureresponse = litle.capture(capture);
		assertEquals(captureresponse.getMessage(), "000",captureresponse.getResponse());
		assertEquals(captureresponse.getMessage(), "Approved",captureresponse.getMessage());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(captureresponse.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test2AVS() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("2");
		authorization.setAmount(000L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Mike J. Hammer");
		contact.setAddressLine1("2 Main St.");
		contact.setAddressLine2("Apt. 222");
		contact.setCity("Riverside");
		contact.setState("RI");
		contact.setZip("02915");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("MC");
		card.setNumber("5112010000000003");
		card.setExpDate("0212");
		card.setCardValidationNum("261");
		authorization.setCard(card);
		FraudCheckType authenticationvalue = new FraudCheckType();
		authenticationvalue.setAuthenticationValue("BwABBJQ1AgAAAAAgJDUCAAAAAAA=");
		authorization.setCardholderAuthentication(authenticationvalue);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "22222",response.getAuthCode());
		assertEquals(response.getMessage(), "10",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
	}
	
	@Test
	public void test2Sale() throws Exception {
		Sale sale = new Sale();
		sale.setOrderId("2");
		sale.setAmount(20020L);
		sale.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Mike J. Hammer");
		contact.setAddressLine1("2 Main St.");
		contact.setAddressLine2("Apt. 222");
		contact.setCity("Riverside");
		contact.setState("RI");
		contact.setZip("02915");
		contact.setCountry(CountryTypeEnum.US);
		sale.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("MC");
		card.setNumber("5112010000000003");
		card.setExpDate("0212");
		card.setCardValidationNum("261");
		sale.setCard(card);
		FraudCheckType authenticationvalue = new FraudCheckType();
		authenticationvalue.setAuthenticationValue("BwABBJQ1AgAAAAAgJDUCAAAAAAA=");
		sale.setCardholderAuthentication(authenticationvalue);
		
		SaleResponse response = litle.sale(sale);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "22222",response.getAuthCode());
		assertEquals(response.getMessage(), "10",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(response.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test3Auth() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("3");
		authorization.setAmount(30030L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Eileen Jones");
		contact.setAddressLine1("3 Main St.");
		contact.setCity("Bloomfield");
		contact.setState("CT");
		contact.setZip("06002");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("DI");
		card.setNumber("6011010000000003");
		card.setExpDate("0312");
		card.setCardValidationNum("758");
		authorization.setCard(card);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "33333",response.getAuthCode());
		assertEquals(response.getMessage(), "10",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
		Capture capture = new Capture();
		capture.setLitleTxnId(response.getLitleTxnId());
		CaptureResponse captureresponse = litle.capture(capture);
		assertEquals(captureresponse.getMessage(), "000",captureresponse.getResponse());
		assertEquals(captureresponse.getMessage(), "Approved",captureresponse.getMessage());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(captureresponse.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test3AVS() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("3");
		authorization.setAmount(000L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Eileen Jones");
		contact.setAddressLine1("3 Main St.");
		contact.setCity("Bloomfield");
		contact.setState("CT");
		contact.setZip("06002");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("DI");
		card.setNumber("6011010000000003");
		card.setExpDate("0312");
		card.setCardValidationNum("758");
		authorization.setCard(card);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "33333",response.getAuthCode());
		assertEquals(response.getMessage(), "10",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
	}
	
	@Test
	public void test3Sale() throws Exception {
		Sale sale = new Sale();
		sale.setOrderId("3");
		sale.setAmount(30030L);
		sale.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Eileen Jones");
		contact.setAddressLine1("3 Main St.");
		contact.setCity("Bloomfield");
		contact.setState("CT");
		contact.setZip("06002");
		contact.setCountry(CountryTypeEnum.US);
		sale.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("DI");
		card.setNumber("6011010000000003");
		card.setExpDate("0312");
		card.setCardValidationNum("758");
		sale.setCard(card);
		
		SaleResponse response = litle.sale(sale);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "33333",response.getAuthCode());
		assertEquals(response.getMessage(), "10",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "M",response.getFraudResult().getCardValidationResult());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(response.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test4Auth() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("4");
		authorization.setAmount(40040L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Bob Black");
		contact.setAddressLine1("4 Main St.");
		contact.setCity("Laurel");
		contact.setState("MD");
		contact.setZip("20708");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("AX");
		card.setNumber("375001000000005");
		card.setExpDate("0412");
		card.setCardValidationNum("758");
		authorization.setCard(card);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "44444",response.getAuthCode());
		assertEquals(response.getMessage(), "12",response.getFraudResult().getAvsResult());
		
		Capture capture = new Capture();
		capture.setLitleTxnId(response.getLitleTxnId());
		CaptureResponse captureresponse = litle.capture(capture);
		assertEquals(captureresponse.getMessage(), "000",captureresponse.getResponse());
		assertEquals(captureresponse.getMessage(), "Approved",captureresponse.getMessage());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(captureresponse.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test4AVS() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("4");
		authorization.setAmount(000L);
		authorization.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Bob Black");
		contact.setAddressLine1("4 Main St.");
		contact.setCity("Laurel");
		contact.setState("MD");
		contact.setZip("20708");
		contact.setCountry(CountryTypeEnum.US);
		authorization.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("AX");
		card.setNumber("375001000000005");
		card.setExpDate("0412");
		card.setCardValidationNum("758");
		authorization.setCard(card);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "44444",response.getAuthCode());
		assertEquals(response.getMessage(), "12",response.getFraudResult().getAvsResult());
		
	}
	
	@Test
	public void test4Sale() throws Exception {
		Sale sale = new Sale();
		sale.setOrderId("4");
		sale.setAmount(40040L);
		sale.setOrderSource("ecommerce");
		Contact contact = new Contact();
		contact.setName("Bob Black");
		contact.setAddressLine1("4 Main St.");
		contact.setCity("Laurel");
		contact.setState("MD");
		contact.setZip("20708");
		contact.setCountry(CountryTypeEnum.US);
		sale.setBillToAddress(contact);
		CardType card = new CardType();
		card.setType("AX");
		card.setNumber("375001000000005");
		card.setExpDate("0412");
		card.setCardValidationNum("758");
		sale.setCard(card);
		
		SaleResponse response = litle.sale(sale);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "44444",response.getAuthCode());
		assertEquals(response.getMessage(), "12",response.getFraudResult().getAvsResult());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(response.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test5Auth() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("5");
		authorization.setAmount(50050L);
		authorization.setOrderSource("ecommerce");
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4457010200000007");
		card.setExpDate("0512");
		card.setCardValidationNum("463");
		authorization.setCard(card);
		FraudCheckType authenticationvalue = new FraudCheckType();
		authenticationvalue.setAuthenticationValue("BwABBJQ1AgAAAAAgJDUCAAAAAAA=");
		authorization.setCardholderAuthentication(authenticationvalue);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "55555 ",response.getAuthCode());
		assertEquals(response.getMessage(), "32",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "N",response.getFraudResult().getCardValidationResult());
		
		Capture capture = new Capture();
		capture.setLitleTxnId(response.getLitleTxnId());
		CaptureResponse captureresponse = litle.capture(capture);
		assertEquals(captureresponse.getMessage(), "000",captureresponse.getResponse());
		assertEquals(captureresponse.getMessage(), "Approved",captureresponse.getMessage());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(captureresponse.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	
	@Test
	public void test5AVS() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setOrderId("5");
		authorization.setAmount(000L);
		authorization.setOrderSource("ecommerce");
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4457010200000007");
		card.setExpDate("0512");
		card.setCardValidationNum("463");
		authorization.setCard(card);
		FraudCheckType authenticationvalue = new FraudCheckType();
		authenticationvalue.setAuthenticationValue("BwABBJQ1AgAAAAAgJDUCAAAAAAA=");
		authorization.setCardholderAuthentication(authenticationvalue);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "55555 ",response.getAuthCode());
		assertEquals(response.getMessage(), "32",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "N",response.getFraudResult().getCardValidationResult());
		
	}
	
	@Test
	public void test5Sale() throws Exception {
		Sale sale = new Sale();
		sale.setOrderId("5");
		sale.setAmount(50050L);
		sale.setOrderSource("ecommerce");
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4457010200000007");
		card.setExpDate("0512");
		card.setCardValidationNum("463");
		sale.setCard(card);
		FraudCheckType authenticationvalue = new FraudCheckType();
		authenticationvalue.setAuthenticationValue("BwABBJQ1AgAAAAAgJDUCAAAAAAA=");
		sale.setCardholderAuthentication(authenticationvalue);
		
		SaleResponse response = litle.sale(sale);
		assertEquals(response.getMessage(), "000",response.getResponse());
		assertEquals(response.getMessage(), "Approved",response.getMessage());
		assertEquals(response.getMessage(), "55555 ",response.getAuthCode());
		assertEquals(response.getMessage(), "32",response.getFraudResult().getAvsResult());
		assertEquals(response.getMessage(), "N",response.getFraudResult().getCardValidationResult());
		
		Credit credit = new Credit();
		credit.setLitleTxnId(response.getLitleTxnId());
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(creditresponse.getMessage(), "000",creditresponse.getResponse());
		assertEquals(creditresponse.getMessage(), "Approved",creditresponse.getMessage());
		
		com.litle.sdk.generate.Void newvoid = new com.litle.sdk.generate.Void();
		newvoid.setLitleTxnId(creditresponse.getLitleTxnId());
		VoidResponse voidresponse = litle.dovoid(newvoid);
		assertEquals(voidresponse.getMessage(), "000",voidresponse.getResponse());
		assertEquals(voidresponse.getMessage(), "Approved",voidresponse.getMessage());
	}
	

}


