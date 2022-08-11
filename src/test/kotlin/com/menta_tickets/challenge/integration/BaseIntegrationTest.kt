package com.menta_tickets.challenge.integration

import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
@TestInstance(PER_CLASS)
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
