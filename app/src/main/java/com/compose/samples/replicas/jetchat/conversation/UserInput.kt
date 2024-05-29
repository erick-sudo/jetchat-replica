package com.compose.samples.replicas.jetchat.conversation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

enum class InputSelector {
    NONE,
    MAP,
    DM,
    EMOJI,
    PHONE,
    PICTURE
}

enum class EmojiStickerSelector {
    EMOJI,
    STICKER
}

@Composable
fun UserInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {}
) {

    var currentInputSelector by rememberSaveable {
        mutableStateOf(InputSelector.NONE)
    }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }

    // Intercept back navigation if there's a InputSelector visible
    if(currentInputSelector != InputSelector.NONE) {
        BackHandler(onBack = dismissKeyboard)
    }
}