package com.menta_tickets.challenge.error.handler

import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class GlobalErrorAttributes : DefaultErrorAttributes() {
    private companion object {
        private const val TRACE = "trace"
        private const val CAUSE = "cause"
        private const val TAB = "\t"
    }

    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any> =
        getError(request).let { error ->
            super.getErrorAttributes(request, options).reformatStackTrace(error).addCause(error)
        }

    private fun MutableMap<String, Any>.reformatStackTrace(error: Throwable?): MutableMap<String, Any> = apply {
        computeIfPresent(TRACE) { _, _ ->
            ExceptionUtils.getStackFrames(error).map { it.replace(TAB, "") }
        }
    }

    private fun MutableMap<String, Any>.addCause(error: Throwable?): MutableMap<String, Any> = apply {
        ExceptionUtils.getRootCauseMessage(error)?.let { rootCauseMessage ->
            if (rootCauseMessage.isNotBlank()) {
                put(CAUSE, rootCauseMessage)
            }
        }
    }
}
