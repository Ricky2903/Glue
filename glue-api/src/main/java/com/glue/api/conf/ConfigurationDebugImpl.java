package com.glue.api.conf;

public class ConfigurationDebugImpl implements Configuration {

	@Override
	public String getUser() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getBaseUrl() {
		// return "http://localhost:8080/glue-webapp/";
		return "http://192.168.0.41:8080/glue-webapp/";
	}

}