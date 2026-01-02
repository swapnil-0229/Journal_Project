package com.sbprojects.journal_app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Journal App API")
                        .description("This API handles user journaling with features like **Sentiment Analysis**, **Weather Integration**, and **Secure Authentication**.\n\n" +
                                     "### Features:\n" +
                                     "* **User Management**: Sign up, login, and profile updates.\n" +
                                     "* **Journal Entries**: CRUD operations for personal logs.\n" +
                                     "* **Sentiment Analysis**: AI-powered weekly mood analysis.\n" +
                                     "* **Weather**: Real-time weather tracking for entries.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Swapnil")
                                .email("swapnil@example.com")
                                .url("https://github.com/swapnil-0229"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server"),
                        new Server().url("https://api.myapp.com").description("Production Server")))
                .tags(List.of(
                        new Tag().name("Public Api's").description("Endpoints accessible without authentication (Login/Signup)."),
                        new Tag().name("User Api's").description("Manage user profile and settings."),
                        new Tag().name("Journal Api's").description("Create, read, update, and delete journal entries."),
                        new Tag().name("Admin Api's").description("Administrative operations for managing users.")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }

    @Bean
    public OpenApiCustomizer sortTagsAlphabetically() {
        return openApi -> {
            List<String> correctOrder = List.of("Public Api's", "User Api's", "Journal Api's", "Admin Api's");
            if (openApi.getTags() != null) {
                List<Tag> sortedTags = openApi.getTags().stream()
                        .sorted(Comparator.comparingInt(tag -> {
                            int index = correctOrder.indexOf(tag.getName());
                            return index >= 0 ? index : 999;
                        }))
                        .collect(Collectors.toList());
                openApi.setTags(sortedTags);
            }
        };
    }
}