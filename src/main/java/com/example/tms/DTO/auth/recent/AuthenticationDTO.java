package com.example.tms.DTO.auth.recent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


public record AuthenticationDTO(@Email(message = "Логин должен быть корректным почтовым адресом")
                                @Schema(description = "Имя пользователя в формате Email")
                                String username,
                                @Schema(description = "Пароль состоит от 2 до 15 символов")
                                @Size(min = 2, max = 15, message = "Пароль должен состоять от 2 до 15 символов")
                                String password) {
}
