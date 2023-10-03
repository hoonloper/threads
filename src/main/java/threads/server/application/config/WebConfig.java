package threads.server.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://threads-front-9gwcg8f2r-hoonlopers-projects.vercel.app/")
//                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .maxAge(3000);
    }
}