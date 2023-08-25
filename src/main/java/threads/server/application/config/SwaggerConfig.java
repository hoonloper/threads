package threads.server.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v0.1.0")
                .title("Threads API")
                .description("내 입맛대로 Threads 클론 API");

        return new OpenAPI()
                .info(info);
    }
}