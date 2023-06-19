package com.example.klasha.config;

import com.example.klasha.exception.KlashaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static feign.FeignException.errorStatus;

@Slf4j
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            InputStream inputStream = response.body().asInputStream();
            String errorJson = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.error("Exception in communication with API: \n" + errorJson);

            Map<String, Object> errorBody = objectMapper.readValue(errorJson, Map.class);

            List<String> errors = !Objects.isNull(errorBody.get("errors")) ? (List<String>) errorBody.get("errors") : new ArrayList<>();

            return new KlashaException(HttpStatus.valueOf(response.status()),
                    errorBody.getOrDefault("message", "Service not currently available, please try again")
                            .toString(), errors, null);
        } catch (IOException e) {
            log.error("Error reading error input stream of feign: " + e.getMessage(), e);
            return errorStatus(methodKey, response);
        }
    }
}
