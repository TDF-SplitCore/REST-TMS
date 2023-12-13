package com.example.tms.DTO.auth.recent;

import com.example.tms.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


public record RegistrationDTO(@Email(message = "Логин должен быть корректным почтовым адресом")
                              @Schema(description = "Имя пользователя в формате Email")
                              String username,
                              @Schema(description = "Пароль состоит от 2 до 15 символов")
                              @Size(min = 2, max = 15, message = "Пароль должен состоять от 2 до 15 символов")
                              String password,
                              @Schema(description = "Выберите роль пользователя, если не указан выставиться USER")
                              Role role) {
    public RegistrationDTO {
        if (role == null) role = Role.USER;
    }
}
