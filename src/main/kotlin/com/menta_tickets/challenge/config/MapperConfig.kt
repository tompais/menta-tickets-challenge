package com.menta_tickets.challenge.config

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.ZoneOffset.UTC
import java.util.TimeZone

@Configuration
class MapperConfig {
    @Bean
    @Primary
    fun mapper(): ObjectMapper = jacksonMapperBuilder()
        .findAndAddModules()
        .serializationInclusion(NON_NULL)
        .serializationInclusion(NON_EMPTY)
        .enable(WRITE_ENUMS_USING_TO_STRING)
        .enable(READ_ENUMS_USING_TO_STRING)
        .enable(ACCEPT_CASE_INSENSITIVE_ENUMS)
        .enable(INDENT_OUTPUT)
        .disable(WRITE_DATES_AS_TIMESTAMPS)
        .disable(FAIL_ON_UNKNOWN_PROPERTIES)
        .defaultTimeZone(TimeZone.getTimeZone(UTC))
        .propertyNamingStrategy(SNAKE_CASE)
        .build()
}
