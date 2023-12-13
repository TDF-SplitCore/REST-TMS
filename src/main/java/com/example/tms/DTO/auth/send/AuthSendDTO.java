package com.example.tms.DTO.auth.send;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthSendDTO(@Schema(description = "Указывает JWT") String jwtToken) {
}
