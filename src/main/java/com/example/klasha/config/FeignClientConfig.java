package com.example.klasha.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
public class FeignClientConfig {
    private final ObjectMapper objectMapper;

//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new FeignErrorDecoder(objectMapper);
//    }

    @Bean
    Logger.Level feignLoggerLevel() {
        if (log.isDebugEnabled()) return Logger.Level.FULL;
        return Logger.Level.BASIC;
    }
}
