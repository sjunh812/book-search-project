package org.sjhstudio.flow.bookproject.presentation.util

import android.widget.ImageView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ImageView.click(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        trySend(Unit)
    }
    awaitClose {
        setOnClickListener(null)
    }
}