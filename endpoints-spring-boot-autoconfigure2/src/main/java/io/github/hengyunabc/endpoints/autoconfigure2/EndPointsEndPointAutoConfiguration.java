package io.github.hengyunabc.endpoints.autoconfigure2;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@ConditionalOnClass(name = { "org.springframework.boot.actuate.endpoint.annotation.Endpoint" })
@Import({ EndPointsEndPointAutoConfiguration.Import.class })
public class EndPointsEndPointAutoConfiguration {

	static class Import {
		@Bean
		@ConditionalOnMissingBean
		public ManagementApplicationcontextHolder managementApplicationcontextHolder() {
			return new ManagementApplicationcontextHolder();
		}

		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnEnabledEndpoint
		public EndPointsEndPoint EndPointsEndPoint() {
			EndPointsEndPoint endPointsEndPoint = new EndPointsEndPoint();
			return endPointsEndPoint;
		}

	}
}
