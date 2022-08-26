package com.menta_tickets.challenge.filters.exchanges.decorators

import com.menta_tickets.challenge.extensions.asString
import org.apache.logging.log4j.kotlin.logger
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import reactor.core.publisher.Flux
import java.io.ByteArrayOutputStream
import java.net.URI

class LoggingRequestDecorator internal constructor(delegate: ServerHttpRequest, shouldLog: Boolean) :
    ServerHttpRequestDecorator(delegate) {

    private companion object {
        private val LOGGER = logger()
    }

    private val body: Flux<DataBuffer>

    override fun getBody(): Flux<DataBuffer> = body

    init {
        body = if (shouldLog) {
            val fullPath = buildFullPath(delegate.uri)
            val method = (delegate.method ?: HttpMethod.GET).name
            val headers = delegate.headers.asString()
            LOGGER.debug("$method $fullPath\n $headers")

            ByteArrayOutputStream().use { bodyStream ->
                DataBufferUtils.write(super.getBody(), bodyStream).doOnComplete {
                    LOGGER.debug("request: $bodyStream")
                }
            }
        } else {
            super.getBody()
        }
    }

    private fun buildFullPath(uri: URI): String = StringBuilder(uri.path).apply {
        val query = uri.query

        if (!query.isNullOrBlank()) {
            append("?$query")
        }
    }.toString()
}
