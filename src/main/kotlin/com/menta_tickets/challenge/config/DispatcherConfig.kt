package com.menta_tickets.challenge.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DispatcherConfig {
    @Bean(destroyMethod = "")
    fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
