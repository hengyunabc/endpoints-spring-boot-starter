package io.github.hengyunabc.endpoints.autoconfigure1;

import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author duanling 2017-07-13
 *
 */
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
