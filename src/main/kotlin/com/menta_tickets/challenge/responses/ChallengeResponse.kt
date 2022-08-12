package com.menta_tickets.challenge.responses

import javax.validation.constraints.PositiveOrZero

data class ChallengeResponse(@field:PositiveOrZero val result: ULong)
