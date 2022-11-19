/**
 * Copyright © 2016-2022 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.monitoring.service.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thingsboard.monitoring.config.MonitoringTargetConfig;
import org.thingsboard.monitoring.config.TransportType;
import org.thingsboard.monitoring.config.service.HttpTransportMonitoringServiceConfig;
import org.thingsboard.monitoring.service.TransportMonitoringService;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HttpTransportMonitoringService extends TransportMonitoringService<HttpTransportMonitoringServiceConfig> {

    private RestTemplate restTemplate;

    protected HttpTransportMonitoringService(HttpTransportMonitoringServiceConfig config, MonitoringTargetConfig target) {
        super(config, target);
    }

    @Override
    protected void initClient() throws Exception {
        if (restTemplate == null) {
            restTemplate = new RestTemplateBuilder()
                    .setConnectTimeout(Duration.ofMillis(config.getRequestTimeoutMs()))
                    .setReadTimeout(Duration.ofMillis(config.getRequestTimeoutMs()))
                    .build();
        }
    }

    @Override
    protected void sendTestPayload(String payload) throws Exception {
        restTemplate.postForObject(target.getBaseUrl() + "/api/v1/" + target.getDevice().getAccessToken() + "/telemetry", payload, String.class);
    }

    @Override
    protected void destroyClient() throws Exception {}

    @Override
    protected TransportType getTransportType() {
        return TransportType.HTTP;
    }

}
