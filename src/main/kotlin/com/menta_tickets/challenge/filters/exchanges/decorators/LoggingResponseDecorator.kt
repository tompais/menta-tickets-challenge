package com.menta_tickets.challenge.filters.exchanges.decorators

import com.menta_tickets.challenge.extensions.asString
import org.apache.logging.log4j.kotlin.logger
import org.reactivestreams.Publisher
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream

class LoggingResponseDecorator internal constructor(delegate: ServerHttpResponse, private val shouldLog: Boolean) :
    ServerHttpResponseDecorator(delegate) {
    private companion object {
        private val LOGGER = logger()
    }

    override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> = super.writeWith(
        ByteArrayOutputStream().use { bodyStream ->
            DataBufferUtils.write(Flux.from(body), bodyStream).doOnComplete {
                if (shouldLog) {
                    LOGGER.debug("response: $bodyStream")
                }
            }
        }
    )

    init {
        if (shouldLog) {
            LOGGER.debug(delegate.headers.asString())
        }
    }
}
