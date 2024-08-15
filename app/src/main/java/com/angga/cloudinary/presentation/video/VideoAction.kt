package com.angga.cloudinary.presentation.video

sealed interface VideoAction {
    data object OnRecordClick : VideoAction
    data object OnStopClick : VideoAction
    data object OnUploadClick : VideoAction
    data object OnChangeCameraClick : VideoAction
}