package com.menta_tickets.challenge.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

object RegexUtils {
    val ONE_CLOSED_STROKE = Regex("[0469ADOPQRabdegijopq@#?¿]+")
    val TWO_CLOSED_STROKES = Regex("[8BÅËÏÜåïüÿ\$%∞&]+")
    val THREE_CLOSED_STROKES = Regex("[ÄÖäëö‰]+")

    fun Regex.countAllAndMultiply(
        input: CharSequence,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        multiplier: UInt = 1u
    ): Flow<ULong> = findAll(input).asFlow()
        .flowOn(dispatcher)
        .map { it.value.count().toULong() * multiplier }
        .flowOn(dispatcher)
}
