package com.example.tms.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
    @Bean

    public OpenAPI customOpenAPI() {
        Info info = new Info();
        info
                .title("Task Management System")
                .description("Система обеспечивает создание, редактирование, удаление и просмотр задач. " +
                        "Каждая задача содержит заголовок, описание, статус (\"в ожидании\", \"в процессе\"," +
                        " \"завершено\") и приоритет (\"высокий\", \"средний\", \"низкий\")," +
                        " а также автора задачи и исполнителя. Имеется авторизация по email и паролю. " +
                        "Пользователи могут управлять своими задачами: создавать новые," +
                        "редактировать существующие, просматривать и удалять, менять статус и назначать " +
                        "исполнителей задачи. Пользователи могут просматривать задачи других пользователей, а" +
                        "исполнители задачи могут менять статус своих задач. К задачам можно оставлять комментарии." +
                        "API позволяет получать задачи конкретного автора или исполнителя, а " +
                        "также все комментарии к ним. Имеется фильтрация по авторам, исполнителям и" +
                        "пагинация вывода.");
        return new OpenAPI().info(info);
    }
}
