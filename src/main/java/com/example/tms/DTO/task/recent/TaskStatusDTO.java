package com.example.tms.DTO.task.recent;

import com.example.tms.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;

public record TaskStatusDTO(@Schema(description = "Статус задачи")
                            Status status) {
}
