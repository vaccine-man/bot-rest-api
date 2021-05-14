package org.covid.vaccineman.exception.handler;

import org.covid.vaccineman.exception.ConstraintViolationException;
import org.covid.vaccineman.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.covid.vaccineman.constant.ExceptionConstants.INVALID_INPUT;
import static org.covid.vaccineman.constant.ExceptionConstants.NO_RECORDS_FOUND;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleMissingFieldException(ConstraintViolationException ex) {
        return buildExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, INVALID_INPUT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return buildExceptionResponse(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, INVALID_INPUT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND, NO_RECORDS_FOUND);
    }

    private static ResponseEntity<Object> buildExceptionResponse(String errorMessage, HttpStatus status, String reason) {
        Map<String, Object> exceptionMap = new HashMap<>();
        exceptionMap.put("status", status.getReasonPhrase());
        exceptionMap.put("error", status);
        exceptionMap.put("reason", reason);
        exceptionMap.put("errorMessage", errorMessage);
        exceptionMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));

        return new ResponseEntity<>(exceptionMap, status);
    }
}
