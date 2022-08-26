package com.menta_tickets.challenge.controllers.interfaces

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

interface IChallengeController {
    suspend fun solveChallenge(serverRequest: ServerRequest): ServerResponse
}
