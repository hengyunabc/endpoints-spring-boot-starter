package io.github.hengyunabc.endpoints.autoconfigure1;

import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


@ManagementContextConfiguration
public class EndPointsEndPointManagementContextConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint("endpoints")
    public EndPointsEndPoint EndPointsEndPoint() {
        EndPointsEndPoint endPointsEndPoint = new EndPointsEndPoint();
        return endPointsEndPoint;
    }

}
