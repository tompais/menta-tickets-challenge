package com.menta_tickets.challenge.config

import org.springframework.context.annotation.Configuration
import java.time.ZoneOffset.UTC
import java.util.TimeZone

@Configuration
class TimeZoneConfig {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }
}
