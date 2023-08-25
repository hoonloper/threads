package threads.server.application.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Threads API",
                description = "내 입맛대로 Threads 클론 API",
                version = "v0.1.0")
)
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi openAPI() {
        String[] paths = {"/api/v1/**"};
        return GroupedOpenApi.builder()
                .group("Threads API")
                .pathsToMatch(paths)
                .build();
    }
}