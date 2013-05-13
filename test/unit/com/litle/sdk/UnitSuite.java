package com.litle.sdk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestEnumerations.class,
	TestLitleOnline.class,
	TestLitleBatchFileRequest.class,
	TestLitleBatchFileResponse.class,
	TestLitleBatchRequest.class,
	TestLitleBatchResponse.class,
})
public class UnitSuite {
}
