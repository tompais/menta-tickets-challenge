package com.menta_tickets.challenge.integration

import com.menta_tickets.challenge.requests.ChallengeRequest
import io.restassured.http.ContentType.JSON
import io.restassured.module.webtestclient.RestAssuredWebTestClient.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.OK

class ChallengeIntegrationTest : BaseIntegrationTest() {
    private companion object {
        @JvmStatic
        private fun solveChallenge(): List<Arguments> = listOf(
            arguments("El 37% de los humanos está bancarizado", 15),
            arguments("Jx31o0Ug", 3),
            arguments("@#\$%∞‰&/()=?¿_-", 15),
            arguments("Are you talking to me?¿", 10)
        )

        @JvmStatic
        private fun cannotSolveChallenge(): List<Arguments> = listOf(
            arguments("{}"),
            arguments(""),
            arguments("^")
        )
    }

    @MethodSource
    @ParameterizedTest
    fun solveChallenge(text: String, result: Int) {
        given()
            .accept(JSON)
            .contentType(JSON)
            .body(ChallengeRequest(text))
            .`when`()
            .post("/challenge/solve")
            .then()
            .assertThat()
            .status(OK)
            .and()
            .body("result", equalTo(result))
    }

    @MethodSource
    @ParameterizedTest
    fun cannotSolveChallenge(text: String) {
        given()
            .accept(JSON)
            .contentType(JSON)
            .body(ChallengeRequest(text))
            .`when`()
            .post("/challenge/solve")
            .then()
            .assertThat()
            .status(BAD_REQUEST)
    }
}
