package com.menta_tickets.challenge.utils

import com.menta_tickets.challenge.utils.RegexUtils.ONE_CLOSED_STROKE
import com.menta_tickets.challenge.utils.RegexUtils.THREE_CLOSED_STROKES
import com.menta_tickets.challenge.utils.RegexUtils.TWO_CLOSED_STROKES
import com.menta_tickets.challenge.utils.RegexUtils.countAllAndMultiply
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList

object CharSequenceUtils {
    suspend fun CharSequence.countClosedStrokes(dispatcher: CoroutineDispatcher = Dispatchers.Default): ULong = flow {
        emitAll(ONE_CLOSED_STROKE.countAllAndMultiply(this@countClosedStrokes, dispatcher, 1u))
        emitAll(
            TWO_CLOSED_STROKES.countAllAndMultiply(this@countClosedStrokes, dispatcher, 2u)
        )
        emitAll(
            THREE_CLOSED_STROKES.countAllAndMultiply(this@countClosedStrokes, dispatcher, 3u)
        )
    }.flowOn(dispatcher).toList().sum()
}
