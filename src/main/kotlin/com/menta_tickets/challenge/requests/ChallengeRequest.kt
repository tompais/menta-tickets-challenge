package com.menta_tickets.challenge.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class ChallengeRequest(
    @field:NotBlank(message = "The text should not be blank")
    @field:Pattern(
        regexp = "[0-9A-Za-zÀ-ÿ @#\$%∞‰&/()=?¿_-]*",
        message = "The text contains invalid chars"
    )
    val text: String
)
