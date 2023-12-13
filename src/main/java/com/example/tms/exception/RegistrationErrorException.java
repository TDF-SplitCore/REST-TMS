package com.example.tms.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;


public class RegistrationErrorException extends BindingResultException{
    public RegistrationErrorException(BindingResult bindingResult) {
        super(bindingResult);
    }
}
