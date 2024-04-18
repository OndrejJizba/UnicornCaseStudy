package com.unicorn.unicorncasestudy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI publicAPI() {
        return new OpenAPI()
                .info(new Info().title("Unicorn Case Study")
                        .description("Case study for interview."));
    }
}
