package com.example.tms.DTO.task.recent;

import com.example.tms.DTO.SortTask;
import com.example.tms.model.SearchType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record TaskSearchDTO(@Min(value = 0, message = "Страниц не может быть меньше 0")
                            @Schema(description = "номер отображаемой страницы начало с 0," +
                                    "если не указан будет 0")
                            Integer page,
                            @Min(value = 1, message = "Не меньше 1")
                            @Max(value = 50, message = "Не больше 50")
                            @Schema(description = "Количество элементов на странице," +
                                    "если не указан будет 20")
                            Integer size,
                            @Schema(description = "Вариант сортировки true = asc, false = desc," +
                                    "если не указан будет true")
                            Boolean asc,
                            @Schema(description = "Указывает по кому производить поиск")
                            SearchType searchType,
                            @Schema(description = "Параметры поиска, полный email пользователя")
                            String searchBy,
                            @Schema(description = "Перечисление по каким полям сортировать")
                            SortTask[] sort) {
    public TaskSearchDTO {
        if (page == null) page = 0;
        if (size == null) size = 20;
        if (sort == null) sort = new SortTask[0];
        if (asc == null) asc = true;
    }
}