package com.menta_tickets.challenge.extensions

import org.springframework.http.HttpHeaders

fun HttpHeaders.asString(): String = toList().joinToString("\n") { "${it.first}: [${it.second.joinToString("; ")}]" }
