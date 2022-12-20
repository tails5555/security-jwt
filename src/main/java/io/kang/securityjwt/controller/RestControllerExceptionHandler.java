package io.kang.securityjwt.controller;

import io.kang.securityjwt.exception.UserDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<?> userDomainException(UserDomainException e) {
        return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                        new HashMap<String, Object>() {{ put("message", e.getMessage()); }}
                    );
    }
}
