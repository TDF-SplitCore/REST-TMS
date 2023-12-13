package com.example.tms.DTO.comment.send;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentSendDTO(@Schema(description = "Идентификатор комментария")
                             Long id,
                             @Schema(description = "Текст комментария")
                             String text,
                             @Schema(description = "Электронная почта автора")
                             String author,
                             @Schema(description = "Заголовок комментария")
                             String taskHeader,
                             @Schema(description = "Дата и время создания комментария")
                             String createDateTime) {
}
