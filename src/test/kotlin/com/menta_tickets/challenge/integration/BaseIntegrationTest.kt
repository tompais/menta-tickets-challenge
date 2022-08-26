package com.menta_tickets.challenge.integration

import io.mockk.junit5.MockKExtension
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@ActiveProfiles("test")
@TestInstance(PER_CLASS)
@ExtendWith(MockKExtension::class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
abstract class BaseIntegrationTest {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @BeforeAll
    fun beforeAll() {
        RestAssuredWebTestClient.applicationContextSetup(applicationContext)
    }

    @AfterAll
    fun afterAll() {
        RestAssuredWebTestClient.reset()
    }
}
