package com.menta_tickets.challenge.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

object RegexUtils {
    val ONE_CLOSED_STROKE = Regex("[0469ADOPQRabdegijopq@#?¿À-ÃÒ-Õà-ãè-êò-õ]+")
    val TWO_CLOSED_STROKES = Regex("[8BÅËÏÜåïüÿ\$%∞&]+")
    val THREE_CLOSED_STROKES = Regex("[ÄÖäëö‰]+")

    suspend fun Regex.countAll(
        input: CharSequence
    ): UInt = findAll(input).asFlow()
        .flowOn(Dispatchers.Default)
        .map { it.value.count().toUInt() }
        .flowOn(Dispatchers.Default)
        .toList()
        .sum()
}
