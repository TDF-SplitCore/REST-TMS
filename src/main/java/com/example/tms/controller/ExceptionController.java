package com.example.tms.controller;

import com.example.tms.DTO.exception.ExceptionsSendDTO;
import com.example.tms.exception.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponse(responseCode = "401",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionsSendDTO.class)))
    public ExceptionsSendDTO badCredentialsException(BadCredentialsException exc) {
        return new ExceptionsSendDTO(List.of(exc.getMessage()));
    }

    @ExceptionHandler(NoAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ApiResponse(responseCode = "403",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionsSendDTO.class)))
    public ExceptionsSendDTO badCredentialsException(NoAccessException exc) {
        return new ExceptionsSendDTO(List.of(exc.getMessage()));
    }

    @ExceptionHandler(NotFoundObjectException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionsSendDTO.class)))
    public ExceptionsSendDTO badCredentialsException(NotFoundObjectException exc) {
        return new ExceptionsSendDTO(List.of(exc.getMessage()));
    }

    @ExceptionHandler({InvalidAuthorizationDataException.class, RegistrationErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionsSendDTO.class)))
    public ExceptionsSendDTO notFoundObjectException(BindingResultException exc) {
        List<String> messages = new ArrayList<>();
        for (FieldError fe : exc.getBindingResult().getFieldErrors()) {
            messages.add(fe.getField() + ": " + fe.getDefaultMessage());
        }
        return new ExceptionsSendDTO(messages);
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionsSendDTO.class)))
    public ExceptionsSendDTO illegalArgumentException() {
        return new ExceptionsSendDTO(List.of("Получены не корректные данные"));
    }

}
