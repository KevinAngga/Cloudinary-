package com.angga.cloudinary.data.repository

import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.domain.model.Video
import com.angga.cloudinary.domain.repository.AndroidCameraController
import com.angga.cloudinary.domain.repository.VideoCaptureRepository
import com.angga.cloudinary.domain.utils.DataError
import com.angga.cloudinary.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoCaptureRepositoryImpl @Inject constructor(
    val cameraDataSource: CameraDataSource
) : VideoCaptureRepository {
    override fun recordVideo(): Flow<Result<Video, DataError.Camera>> {
        TODO("Not yet implemented")
    }
}