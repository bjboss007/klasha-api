package com.example.klasha.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;


@Getter
public class KlashaException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final List<String> errors;
    private final Object data;

    public KlashaException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public KlashaException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, Collections.singletonList(message), (Object)null);
    }

    public KlashaException(HttpStatus httpStatus, String message, Object data) {
        this(httpStatus, message, Collections.singletonList(message), data);
    }

    public KlashaException(HttpStatus httpStatus, String message, List<String> errors, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
        this.data = data;
    }

}
