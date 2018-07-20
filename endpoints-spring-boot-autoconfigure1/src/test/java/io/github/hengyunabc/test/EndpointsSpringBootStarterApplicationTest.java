package io.github.hengyunabc.test;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EndpointsSpringBootStarterApplicationTest.Application.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"management.security.enabled=false" })
public class EndpointsSpringBootStarterApplicationTest {

	@Value("${local.management.port}")
	int managementPort;

	@Value("${local.server.port}")
	int serverPort;

	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testEndpoint() throws Exception {
		System.err.println("serverPort: " + serverPort);
		System.err.println("managementPort: " + managementPort);

		System.in.read();
		String mappings = restTemplate.getForObject("http://localhost:" + managementPort + "/endpoints/mappings",
				String.class);
		Assertions.assertThat(mappings).contains("/endpoints/mappings");
	}

	@SpringBootApplication
	public static class Application {
	}

}
