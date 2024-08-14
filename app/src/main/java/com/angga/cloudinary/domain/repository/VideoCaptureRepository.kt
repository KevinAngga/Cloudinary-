package com.angga.cloudinary.domain.repository

import com.angga.cloudinary.domain.utils.Result
import com.angga.cloudinary.domain.model.Video
import com.angga.cloudinary.domain.utils.DataError
import kotlinx.coroutines.flow.Flow


interface VideoCaptureRepository {
    fun recordVideo() : Flow<Result<Video, DataError.Camera>>
//    fun getCameraController() : AndroidCameraController
}