package com.angga.cloudinary.domain.repository

import com.angga.cloudinary.domain.model.Video
import com.angga.cloudinary.domain.utils.DataError
import com.angga.cloudinary.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface AndroidCameraController {
    fun captureVideo(): Flow<Result<Video, DataError.Camera>>
    fun stopRecording()
}