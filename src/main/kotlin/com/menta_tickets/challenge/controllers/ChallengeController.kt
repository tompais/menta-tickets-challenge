package com.menta_tickets.challenge.controllers

import com.menta_tickets.challenge.services.interfaces.IChallengeService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json
import javax.validation.Validator

@Component
class ChallengeController(validator: Validator, private val challengeService: IChallengeService) :
    BaseController(validator) {
    suspend fun solveChallenge(serverRequest: ServerRequest): ServerResponse = ServerResponse.ok().json()
        .bodyValueAndAwait(challengeService.solveChallenge(serverRequest.getAndValidateBody()))
}
