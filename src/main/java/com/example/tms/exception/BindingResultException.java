package com.example.tms.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
@Getter
public class BindingResultException extends Exception{
    private final BindingResult bindingResult;

    public BindingResultException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}