package io.github.hengyunabc.endpoints.autoconfigure2;

import org.springframework.context.ConfigurableApplicationContext;

public class ManagementApplicationcontextHolder {

	private ConfigurableApplicationContext managementApplicationContext;

	public ConfigurableApplicationContext getManagementApplicationContext() {
		return managementApplicationContext;
	}

	public void setManagementApplicationContext(ConfigurableApplicationContext managementApplicationContext) {
		this.managementApplicationContext = managementApplicationContext;
	}

}
