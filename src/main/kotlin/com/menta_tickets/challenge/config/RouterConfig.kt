package com.menta_tickets.challenge.config

import com.menta_tickets.challenge.controllers.implementations.ChallengeController
import com.menta_tickets.challenge.controllers.interfaces.IChallengeController
import com.menta_tickets.challenge.requests.ChallengeRequest
import com.menta_tickets.challenge.responses.ChallengeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfig {
    @Bean
    @RouterOperation(
        beanClass = ChallengeController::class,
        beanMethod = "solveChallenge",
        operation = Operation(
            operationId = "solveChallenge",
            summary = "Endpoint to resolve the challenge",
            requestBody = RequestBody(
                required = true,
                content = [
                    Content(
                        schema = Schema(implementation = ChallengeRequest::class)
                    )
                ]
            ),
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "The challenge was successfully solved",
                    content = [
                        Content(
                            schema = Schema(implementation = ChallengeResponse::class)
                        )
                    ]
                ),
                ApiResponse(
                    responseCode = "400",
                    description = "The given text is invalid"
                )
            ]
        )
    )
    fun challengeRoutes(challengeController: IChallengeController) = coRouter {
        accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON)).nest {
            POST("/challenge/solve", challengeController::solveChallenge)
        }
    }
}
