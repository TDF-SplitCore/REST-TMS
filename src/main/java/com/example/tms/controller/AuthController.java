package com.example.tms.controller;

import com.example.tms.DTO.auth.recent.AuthenticationDTO;
import com.example.tms.DTO.auth.recent.RegistrationDTO;
import com.example.tms.DTO.auth.send.AuthSendDTO;
import com.example.tms.converter.Converter;
import com.example.tms.exception.InvalidAuthorizationDataException;
import com.example.tms.exception.RegistrationErrorException;
import com.example.tms.jwt.JWTUtil;
import com.example.tms.model.User;
import com.example.tms.service.UserRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRegistrationService userRegistrationService;

    public AuthController(JWTUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          UserRegistrationService userRegistrationService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST",
            summary = "Регистрация",
            description = "Регистрация и получение JWT")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthSendDTO.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(implementation = RegistrationDTO.class)))
    public AuthSendDTO registrationUser(@Valid @RequestBody RegistrationDTO userDTO,
                                        BindingResult bindingResult) throws RegistrationErrorException {
        User user = Converter.convertToUser(userDTO);
        userRegistrationService.validateUsername(user.getUsername(), bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RegistrationErrorException(bindingResult);
        }
        userRegistrationService.registration(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthSendDTO(token);
    }

    @PostMapping("/login")
    @Operation(method = "POST",
            summary = "Авторизация",
            description = "Авторизация и получение JWT")
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthSendDTO.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(implementation = AuthenticationDTO.class)))
    public AuthSendDTO authorizationUser(@Valid @RequestBody AuthenticationDTO authenticationDTO,
                                         BindingResult bindingResult) throws InvalidAuthorizationDataException {
        if (bindingResult.hasErrors()) {
            throw new InvalidAuthorizationDataException(bindingResult);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        authenticationManager.authenticate(authenticationToken);
        String token = jwtUtil.generateToken(authenticationDTO.username());
        return new AuthSendDTO(token);
    }
}
