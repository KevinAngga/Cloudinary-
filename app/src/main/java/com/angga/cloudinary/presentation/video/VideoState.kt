package com.angga.cloudinary.presentation.video

import android.net.Uri

data class VideoState(
    val videoUri : Uri? = null,
    val showDialog : Boolean = false,
    val isRecording : Boolean = false,
    val isUploading : Boolean = false,
    val isFinishUpload : Boolean = false
)
