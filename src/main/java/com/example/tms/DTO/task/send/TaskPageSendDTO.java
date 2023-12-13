package com.example.tms.DTO.task.send;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;

import java.util.List;

public record TaskPageSendDTO(@Schema(description = "Выдает Задачи с ссылкой на комментарии")
                              List<TaskPageElementSendDTO> content,
                              @Schema(description = "Передает итоговое кол-во страниц")
                              int totalPages,
                              @Schema(description = "Итоговое количество комментариев")
                              long totalElements,
                              @Schema(description = "true если последняя страница")
                              boolean last,
                              @Schema(description = "true если первая страница")
                              boolean first,
                              @Schema(description = "Количество комментариев на странице")
                              int size,
                              @Schema(description = "Номер страницы")
                              int number,
                              Sort sort,
                              @Schema(description = "Кол-во элементов на странице")
                              int numberOfElements,
                              @Schema(description = "Пустая ли страница")
                              boolean empty) {

}
