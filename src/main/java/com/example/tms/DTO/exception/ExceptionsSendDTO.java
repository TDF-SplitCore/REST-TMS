package com.example.tms.DTO.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ExceptionsSendDTO(@Schema(description = "Список сообщений об ошибках") List<String> messages) {
}
