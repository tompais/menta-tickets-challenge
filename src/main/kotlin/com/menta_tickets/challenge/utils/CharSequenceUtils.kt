package com.menta_tickets.challenge.utils

import com.menta_tickets.challenge.utils.RegexUtils.ONE_CLOSED_STROKE
import com.menta_tickets.challenge.utils.RegexUtils.THREE_CLOSED_STROKES
import com.menta_tickets.challenge.utils.RegexUtils.TWO_CLOSED_STROKES
import com.menta_tickets.challenge.utils.RegexUtils.countAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

object CharSequenceUtils {
    suspend fun CharSequence.countClosedStrokes(): UInt = supervisorScope {
        withContext(Dispatchers.Default) {
            val deferredValue1 = async { ONE_CLOSED_STROKE.countAll(this@countClosedStrokes) }
            val deferredValue2 = async { TWO_CLOSED_STROKES.countAll(this@countClosedStrokes) * 2u }
            val deferredValue3 = async { THREE_CLOSED_STROKES.countAll(this@countClosedStrokes) * 3u }

            deferredValue1.await() + deferredValue2.await() + deferredValue3.await()
        }
    }
}
