package com.menta_tickets.challenge.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

object RegexUtils {
    val ONE_CLOSED_STROKE = Regex("[0469ADOPQRabdegijopq@#?¿À-ÃÒ-Õà-ãè-êò-õ]+")
    val TWO_CLOSED_STROKES = Regex("[8BÅËÏÜåïüÿ\$%∞&]+")
    val THREE_CLOSED_STROKES = Regex("[ÄÖäëö‰]+")

    fun Regex.countAllAndMultiply(
        input: CharSequence,
        multiplier: UInt = 1u
    ): Flow<ULong> = findAll(input).asFlow()
        .flowOn(Dispatchers.Default)
        .map { it.value.count().toULong() * multiplier }
        .flowOn(Dispatchers.Default)
}
