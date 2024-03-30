package com.bank.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/error")
    public Mono<String> getFallback() {
        return Mono.just("An error occurred! Please try after sometime.");
    }

}
