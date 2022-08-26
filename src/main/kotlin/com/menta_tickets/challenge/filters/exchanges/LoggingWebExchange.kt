package com.menta_tickets.challenge.filters.exchanges

import com.menta_tickets.challenge.filters.exchanges.decorators.LoggingRequestDecorator
import com.menta_tickets.challenge.filters.exchanges.decorators.LoggingResponseDecorator
import org.apache.logging.log4j.kotlin.logger
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.util.AntPathMatcher
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebExchangeDecorator

class LoggingWebExchange(delegate: ServerWebExchange) : ServerWebExchangeDecorator(delegate) {
    private companion object {
        private val LOGGER = logger()
        private val EXCLUDE_URL_PATTERNS =
            setOf(
                "/",
                "/favicon.ico",
                "/actuator/**",
                "/profile/**",
                "/explorer/**",
                "/swagger*/**",
                "/v3/api-docs/**",
                "/webjars/**"
            )
        private val PATH_MATCHER = AntPathMatcher()
    }

    private val requestDecorator = LoggingRequestDecorator(delegate.request, shouldLog())
    private val responseDecorator = LoggingResponseDecorator(delegate.response, shouldLog())

    override fun getRequest(): ServerHttpRequest = requestDecorator

    override fun getResponse(): ServerHttpResponse = responseDecorator

    private fun shouldLog(): Boolean = LOGGER.delegate.isDebugEnabled && !shouldExcludePath()

    private fun shouldExcludePath(): Boolean {
        val path = delegate.request.uri.path

        return EXCLUDE_URL_PATTERNS
            .any { excludeUrlPattern ->
                PATH_MATCHER.match(
                    excludeUrlPattern,
                    path
                )
            }
    }
}
