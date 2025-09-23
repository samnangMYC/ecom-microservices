package com.ecommerce.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class GatewayConfig {

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1,1,1);
    }
    @Bean
    KeyResolver hostNameResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getHostName()
        );
    }
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .filters(f -> f
                                //.rewritePath("/api/(?<segment>.*)", "/${segment}")
                                .retry(retryConfig -> {
                                    retryConfig.setRetries(10)
                                            .setMethods(HttpMethod.GET);
                                })
                                .circuitBreaker(config -> config
                                        .setName("ecomBreaker")
                                        .setFallbackUri("forward:/fallback/products")
                                )
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(redisRateLimiter())
                                            .setKeyResolver(hostNameResolver());
                                })
                        )
                        .uri("lb://PRODUCT"))
                .route("order-service", r -> r
                        .path("/api/orders/**", "/api/cart/**")
                        .filters(f -> f
                                //.rewritePath("/api/(?<segment>.*)", "/${segment}")
                                .circuitBreaker(config -> config
                                        .setName("orderBreaker")
                                        .setFallbackUri("forward:/fallback/orders")
                                )
                        )
                        .uri("lb://ORDER"))
                .route("user-service", r -> r
                        .path("/api/users/**")
//                        .filters(f ->
//                                f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://USER"))
                .build();
    }


}
