package com.mercadolibre.ipcontext.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class IpContextExceptionHandler {

    @ExceptionHandler({
            IpAddressIsBannedException.class,
            ClientApiErrorException.class,
            RuntimeException.class
    })
    public ResponseEntity<ErrorMessageCustom> handleGeneralException(RuntimeException exception) {
        return ResponseEntity
                .badRequest()
                .body(errorMessageCustom(exception.getMessage()));
    }

    private ErrorMessageCustom errorMessageCustom(String detail) {
        return ErrorMessageCustom.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .code(HttpStatus.BAD_REQUEST.value())
                .detail(detail)
                .build();
    }

}
