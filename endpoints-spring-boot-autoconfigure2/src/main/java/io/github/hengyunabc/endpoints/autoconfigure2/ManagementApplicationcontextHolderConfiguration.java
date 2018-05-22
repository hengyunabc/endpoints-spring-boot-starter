package io.github.hengyunabc.endpoints.autoconfigure2;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@ManagementContextConfiguration
public class ManagementApplicationcontextHolderConfiguration {

	@Autowired
	private ConfigurableApplicationContext context;

	@PostConstruct
	public void init() {
		ManagementApplicationcontextHolder holder = context.getBean(ManagementApplicationcontextHolder.class);
		holder.setManagementApplicationContext(context);
	}

}
