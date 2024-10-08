package com.angga.cloudinary.presentation.ui.video

import com.angga.cloudinary.presentation.ui.utils.UiText

sealed interface VideoEvent {
    data class Error(val error : UiText) : VideoEvent
    data object Success : VideoEvent
}