package org.covid.vaccineman.exception;

public class ConstraintViolationException extends Exception{
    public ConstraintViolationException(String errorMessage) {
        super(errorMessage);
    }
}
