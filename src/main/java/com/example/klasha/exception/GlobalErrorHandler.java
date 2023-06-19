package com.example.klasha.exception;

import com.example.klasha.dto.CustomResponse;
import com.example.klasha.dto.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;

@RestControllerAdvice
@RestController
@Slf4j
public class GlobalErrorHandler implements ErrorController {

    @ExceptionHandler({KlashaException.class})
    public ResponseEntity<CustomResponse> handleThrowable(KlashaException e) {
        log.warn(e.getMessage());
        CustomResponse errorResponse = CustomResponse.builder().code(e.getHttpStatus().value()).message(e.getMessage()).type(e.getHttpStatus().is5xxServerError() ? ErrorType.SERVER_ERROR : ErrorType.ERROR).errors(e.getErrors()).build();
        errorResponse.setTimestamp(new Date());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }


    @RequestMapping({"/error"})
    public ResponseEntity<CustomResponse> handleError(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus httpStatus = this.getHttpStatus(request);
        log.debug("status code in handleError: " + httpStatus);
        String message = this.getErrorMessage(request, httpStatus);
        log.debug("error message in handleError: " + message);
        CustomResponse errorResponse = CustomResponse.builder().code(httpStatus.value()).message(message).type(httpStatus.is5xxServerError() ? ErrorType.SERVER_ERROR : ErrorType.ERROR).errors(Collections.singletonList(message)).build();
        errorResponse.setTimestamp(new Date());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private HttpStatus getHttpStatus(HttpServletRequest request) {
        String code = request.getParameter("code");
        Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");
        HttpStatus httpStatus;
        if (status != null) {
            httpStatus = HttpStatus.valueOf(status);
        } else if (!StringUtils.isEmpty(code)) {
            httpStatus = HttpStatus.valueOf(code);
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return httpStatus;
    }

    private String getErrorMessage(HttpServletRequest request, HttpStatus httpStatus) {
        String message = (String)request.getAttribute("javax.servlet.error.message");
        if (message != null && !message.isEmpty()) {
            return message;
        } else {
            switch (httpStatus) {
                case NOT_FOUND:
                    message = "The resource does not exist";
                    break;
                case INTERNAL_SERVER_ERROR:
                    message = "Something went wrong internally";
                    break;
                case FORBIDDEN:
                    message = "Permission denied";
                    break;
                case TOO_MANY_REQUESTS:
                    message = "Too many requests";
                    break;
                default:
                    message = httpStatus.getReasonPhrase();
            }

            return message;
        }
    }

    public String getErrorPath() {
        return "/error";
    }
}
