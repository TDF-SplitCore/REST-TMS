package com.example.tms.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

public class InvalidAuthorizationDataException extends BindingResultException{
    public InvalidAuthorizationDataException(BindingResult bindingResult) {
        super(bindingResult);
    }
}