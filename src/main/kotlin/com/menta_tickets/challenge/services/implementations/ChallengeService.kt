package com.menta_tickets.challenge.services.implementations

import com.menta_tickets.challenge.requests.ChallengeRequest
import com.menta_tickets.challenge.responses.ChallengeResponse
import com.menta_tickets.challenge.services.interfaces.IChallengeService
import com.menta_tickets.challenge.utils.CharSequenceUtils.countClosedStrokes
import kotlinx.coroutines.CoroutineDispatcher
import org.springframework.stereotype.Service

@Service
class ChallengeService(private val defaultDispatcher: CoroutineDispatcher) : IChallengeService {
    override suspend fun solveChallenge(challengeRequest: ChallengeRequest): ChallengeResponse =
        challengeRequest.text.countClosedStrokes(defaultDispatcher).let(::ChallengeResponse)
}
