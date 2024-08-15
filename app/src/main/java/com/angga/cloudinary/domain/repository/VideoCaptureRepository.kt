package com.angga.cloudinary.domain.repository

import com.angga.cloudinary.domain.model.UploadProgress
import com.angga.cloudinary.domain.utils.DataError
import com.angga.cloudinary.domain.utils.Result
import kotlinx.coroutines.flow.Flow


interface VideoCaptureRepository {
    fun uploadVideo(videoUri: String) : Flow<Result<UploadProgress, DataError.Uploader>>
    fun cancelUpload()
}