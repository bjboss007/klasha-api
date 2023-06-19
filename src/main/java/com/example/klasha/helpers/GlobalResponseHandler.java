package com.example.klasha.helpers;

import com.example.klasha.dto.CustomResponse;
import com.example.klasha.dto.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalResponseHandler {

    public GlobalResponseHandler() {
    }

    public static CustomResponse generic400BadRequest(String errors) {
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setType(ErrorType.ERROR);
        response.setErrors(Collections.singletonList(errors));
        response.setMessage(errors);
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(new Date());
        return response;
    }

    public static CustomResponse generic404NotFoundRequest(String errors) {
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setType(ErrorType.ERROR);
        response.setErrors(Collections.singletonList(errors));
        response.setMessage(errors);
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(new Date());
        return response;
    }

    public static CustomResponse generic401UnauthorizedRequest(String errors) {
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setType(ErrorType.ERROR);
        response.setErrors(Collections.singletonList(errors));
        response.setMessage(errors);
        response.setCode(HttpStatus.UNAUTHORIZED.value());
        response.setTimestamp(new Date());
        return response;
    }

    public static CustomResponse genericValidationErrors(List<String> errors) {
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setType(ErrorType.VALIDATION_ERROR);
        response.setErrors(errors);
        response.setMessage("Missing required parameter(s)");
        response.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setTimestamp(new Date());
        return response;
    }

    public static CustomResponse genericInternalServerError(String message) {
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setType(ErrorType.SERVER_ERROR);
        response.setErrors(Collections.singletonList(message != null ? message : "Oops! We're unable to process this request now."));
        response.setMessage(message != null ? message : "Oops! We're unable to process this request now.");
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimestamp(new Date());
        return response;
    }

    public static CustomResponse generic200Response(String message) {
        return generic200Response(message, (Object)null);
    }

    public static CustomResponse generic200Response(Object data) {
        return generic200Response((String)null, data);
    }

    public static CustomResponse generic200Response(String message, Object data) {
        CustomResponse response = new CustomResponse();
        response.setStatus(true);
        response.setMessage(message != null && !message.isEmpty() ? message : "Operation Successful");
        response.setCode(HttpStatus.OK.value());
        response.setData(data);
        response.setTimestamp(new Date());
        response.setErrors((List)null);
        return response;
    }

    public static CustomResponse generic201Response(String message, Object data) {
        CustomResponse response = new CustomResponse();
        response.setStatus(true);
        response.setMessage(message != null && !message.isEmpty() ? message : "Creation Successful");
        response.setCode(HttpStatus.CREATED.value());
        response.setData(data);
        response.setTimestamp(new Date());
        return response;
    }

    public static CustomResponse generic201Response(String message) {
        return generic201Response(message, (Object)null);
    }

    public static CustomResponse generic201Response(Object data) {
        return generic201Response((String)null, data);
    }

    public static CustomResponse genericResponse(HttpStatus status, String message, Map<String, Object> data) {
        CustomResponse response = new CustomResponse();
        response.setStatus(true);
        response.setMessage(message);
        response.setCode(status.value());
        response.setData(data);
        response.setTimestamp(new Date());
        return response;
    }

    public static CustomResponse genericErrorResponse(HttpStatus status, String message) {
        return genericErrorResponse(status, message, Collections.singletonList(message));
    }

    public static CustomResponse genericErrorResponse(HttpStatus status, String message, List<String> errors) {
        return genericErrorResponse(status, message, errors, (Map)null);
    }

    public static CustomResponse genericErrorResponse(HttpStatus status, String message, Map<String, Object> data) {
        if (StringUtils.isEmpty(message)) {
            message = status.getReasonPhrase();
        }

        return genericErrorResponse(status, message, Collections.singletonList(message), data);
    }

    public static CustomResponse genericErrorResponse(HttpStatus status, String message, List<String> errors, Map<String, Object> data) {
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setType(ErrorType.ERROR);
        response.setMessage(message);
        response.setErrors(errors);
        response.setCode(status.value());
        response.setData(data);
        response.setTimestamp(new Date());
        return response;
    }

}
