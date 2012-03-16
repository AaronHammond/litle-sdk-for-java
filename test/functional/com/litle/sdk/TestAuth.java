package com.litle.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.BeforeClass;
import org.junit.Test;

import com.litle.sdk.generate.Authorization;
import com.litle.sdk.generate.AuthorizationResponse;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.PayPal;
import com.litle.sdk.generate.Pos;
import com.litle.sdk.generate.PosCardholderIdTypeEnum;

public class TestAuth {

	private static LitleOnline litle;

	@BeforeClass
	public static void beforeClass() throws Exception {
		litle = new LitleOnline();
	}
	
	@Test
	public void simpleAuthWithCard() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(BigInteger.valueOf(106L));
		authorization.setOrderSource("ecommerce");
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "000",response.getResponse());
	}
	
	@Test
	public void simpleAuthWithPaypal() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("123456");
		authorization.setAmount(BigInteger.valueOf(106L));
		authorization.setOrderSource("ecommerce");
		PayPal paypal = new PayPal();
		paypal.setPayerId("1234");
		paypal.setToken("1234");
		paypal.setTransactionId("123456");
		authorization.setPaypal(paypal);
		
		AuthorizationResponse response = litle.authorize(authorization);
		assertEquals(response.getMessage(), "Approved",response.getMessage());
	}
	
	@Test
	public void illegalOrderSource() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(BigInteger.valueOf(106L));
		authorization.setOrderSource("ecomerce"); //This order source is mispelled on purpose!
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);
		
		try {
			litle.authorize(authorization);
			fail("expected exception");
		} catch(LitleOnlineException e) {
			assertEquals("Error validating xml data against the schema", e.getMessage());
		}
	}
	
	@Test
	public void posWithoutCapabilityAndEntryMode() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(BigInteger.valueOf(106L));
		authorization.setOrderSource("ecommerce");
		Pos pos = new Pos();
		pos.setCardholderId(PosCardholderIdTypeEnum.PIN);
		authorization.setPos(pos);
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);
		
		try {
			litle.authorize(authorization);
			fail("expected exception");
		} catch(LitleOnlineException e) {
			assertEquals("Error validating xml data against the schema", e.getMessage());
		}
	}

}
