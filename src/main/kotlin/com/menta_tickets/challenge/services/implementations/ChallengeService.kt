package com.menta_tickets.challenge.services.implementations

import com.menta_tickets.challenge.requests.ChallengeRequest
import com.menta_tickets.challenge.responses.ChallengeResponse
import com.menta_tickets.challenge.services.interfaces.IChallengeService
import com.menta_tickets.challenge.utils.CharSequenceUtils.countClosedStrokes
import org.springframework.stereotype.Service

@Service
class ChallengeService : IChallengeService {
    override suspend fun solveChallenge(challengeRequest: ChallengeRequest): ChallengeResponse =
        challengeRequest.text.countClosedStrokes().let(::ChallengeResponse)
}
