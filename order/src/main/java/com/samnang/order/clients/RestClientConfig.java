package com.samnang.order.clients;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Configuration
public class RestClientConfig {

    @Autowired(required = false)
    private ObservationRegistry observationRegistry;

    @Autowired(required = false)
    private Tracer tracer;

    @Autowired(required = false)
    private Propagator propagator;

    @Bean(name = "loadBalancedRestClientBuilder")
    @LoadBalanced
    public RestClient.Builder restClientBuilder() {
        RestClient.Builder clientBuilder = RestClient.builder();

        if (observationRegistry != null && tracer != null && propagator != null) {
            clientBuilder.requestInterceptor(createTracingInterceptor());
        }

        return clientBuilder;
    }

    private ClientHttpRequestInterceptor createTracingInterceptor() {
        return (request, body, execution) -> {
            if (tracer != null && propagator != null) {
                propagator.inject(
                        Objects.requireNonNull(tracer.currentTraceContext().context()),
                        request.getHeaders(),
                        (carrier, key, value) -> {
                            assert carrier != null;
                            carrier.add(key, value);
                        }
                );
            }

            return execution.execute(request, body);
        };
    }
}
