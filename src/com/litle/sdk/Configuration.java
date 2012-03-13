package com.litle.sdk;

import java.io.File;

public class Configuration {
	
	private static final String LITLE_SDK_CONFIG = ".litle_sdk_config.properties";

	public static File location() {
		File file = new File(System.getenv("HOME") + File.separator + LITLE_SDK_CONFIG);
		if(System.getenv("LITLE_CONFIG_DIR") != null) {
			file = new File(System.getenv("LITLE_CONFIG_DIR") + File.separator + LITLE_SDK_CONFIG);
		}
		return file;
	}
}
