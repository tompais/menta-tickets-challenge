package com.menta_tickets.challenge.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun modelResolver(mapper: ObjectMapper): ModelResolver = ModelResolver(mapper)

    @Bean
    fun openAPI(): OpenAPI = OpenAPI().info(
        Info()
            .title("Menta Tickets Challenge Solver")
            .description("API that will solve Menta Tickets Challenge")
    )
}
