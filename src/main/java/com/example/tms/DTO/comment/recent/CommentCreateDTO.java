package com.example.tms.DTO.comment.recent;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentCreateDTO(@Schema(description = "Ввод текста сообщения") String text) {
}
