package com.menta_tickets.challenge.services.interfaces

import com.menta_tickets.challenge.requests.ChallengeRequest
import com.menta_tickets.challenge.responses.ChallengeResponse

interface IChallengeService {
    suspend fun solveChallenge(challengeRequest: ChallengeRequest): ChallengeResponse
}
