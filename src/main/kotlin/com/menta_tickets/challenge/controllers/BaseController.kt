package com.menta_tickets.challenge.controllers

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.server.ResponseStatusException
import javax.validation.Validator

abstract class BaseController(protected val validator: Validator) {
    protected suspend inline fun <reified T : Any> ServerRequest.getAndValidateBody(): T =
        awaitBody<T>().also { body ->
            validateBody(body)
        }

    protected inline fun <reified T : Any> validateBody(body: T) {
        validator.validate(body)?.map { violation ->
            "${violation.rootBeanClass.name} ${violation.propertyPath}: ${violation.message}"
        }?.let { errors ->
            if (errors.isNotEmpty()) {
                throw ResponseStatusException(BAD_REQUEST, errors.toString())
            }
        }
    }
}
