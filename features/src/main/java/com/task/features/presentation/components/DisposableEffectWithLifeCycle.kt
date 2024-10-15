package com.task.features.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun DisposableEffectWithLifeCycle(
    onStart: () -> Unit,
    onDestroy: () -> Unit,
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnDestroy by rememberUpdatedState(onDestroy)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {

                Lifecycle.Event.ON_START -> {
                    currentOnStart()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    currentOnDestroy()
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

}