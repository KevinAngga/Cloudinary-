package com.angga.cloudinary.data.utils

import androidx.camera.video.VideoRecordEvent.Finalize.ERROR_ENCODING_FAILED
import androidx.camera.video.VideoRecordEvent.Finalize.ERROR_FILE_SIZE_LIMIT_REACHED
import androidx.camera.video.VideoRecordEvent.Finalize.ERROR_INSUFFICIENT_STORAGE
import androidx.camera.video.VideoRecordEvent.Finalize.ERROR_INVALID_OUTPUT_OPTIONS
import androidx.camera.video.VideoRecordEvent.Finalize.ERROR_NO_VALID_DATA
import androidx.camera.video.VideoRecordEvent.Finalize.ERROR_RECORDER_ERROR
import androidx.camera.video.VideoRecordEvent.Finalize.ERROR_SOURCE_INACTIVE
import com.angga.cloudinary.domain.utils.DataError

fun handleCameraError(errorCode : Int) : DataError.Camera {
    return when(errorCode) {
        ERROR_FILE_SIZE_LIMIT_REACHED -> { DataError.Camera.FILE_SIZE_LIMIT_REACHED }
        ERROR_INSUFFICIENT_STORAGE -> { DataError.Camera.INSUFFICIENT_STORAGE }
        ERROR_INVALID_OUTPUT_OPTIONS -> { DataError.Camera.INVALID_OUTPUT_OPTIONS }
        ERROR_ENCODING_FAILED -> { DataError.Camera.ENCODING_FAILED }
        ERROR_RECORDER_ERROR -> { DataError.Camera.RECORDER_ERROR }
        ERROR_NO_VALID_DATA -> { DataError.Camera.NO_VALID_DATA }
        ERROR_SOURCE_INACTIVE -> { DataError.Camera.SOURCE_INACTIVE }
        else -> {
            DataError.Camera.UNKNOWN
        }
    }
}