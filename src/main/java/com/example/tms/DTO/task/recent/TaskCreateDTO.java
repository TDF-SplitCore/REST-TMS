package com.example.tms.DTO.task.recent;

import com.example.tms.model.Priority;
import com.example.tms.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;

public record TaskCreateDTO(@Schema(description = "Заголовок задачи")
                            String header,
                            @Schema(description = "Описание задачи")
                            String description,
                            @Schema(description = "Приоритет")
                            Priority priority,
                            @Schema(description = "Статус задачи")
                            Status status,
                            @Schema(description = "Электронная почта исполнителя")
                            String executor) {
}
