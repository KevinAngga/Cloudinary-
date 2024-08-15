package com.angga.cloudinary.presentation.ui.video

sealed interface VideoAction {
    data object OnRecordClick : VideoAction
    data object OnStopClick : VideoAction
    data object OnUploadClick : VideoAction
    data object OnChangeCameraClick : VideoAction
    data class OnCheckPermission(val hasAllPermission : Boolean) : VideoAction
}