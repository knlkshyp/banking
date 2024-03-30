package com.bank.gatewayserver.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class BankRoutes {

    @Bean
    public RouteLocator routeLocatorBean(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(path -> path.path("/bank/account/**")
                        .filters(filter -> filter.rewritePath("/bank/account/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountCircuitBreaker").setFallbackUri("forward:/error")))
                        .uri("lb://ACCOUNT"))
                .route(path -> path.path("/bank/card/**")
                        .filters(filter -> filter.rewritePath("/bank/card/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARD"))
                .route(path -> path.path("/bank/loan/**")
                        .filters(filter -> filter.rewritePath("/bank/loan/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://LOAN"))
                .build();
    }
}
