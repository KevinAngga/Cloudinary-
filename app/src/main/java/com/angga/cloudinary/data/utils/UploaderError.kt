package com.angga.cloudinary.data.utils

import com.angga.cloudinary.domain.utils.DataError
import com.cloudinary.android.callback.ErrorInfo

fun handleUploaderError(errorCode : ErrorInfo) : DataError.Uploader {
    return when(errorCode.code) {
        ErrorInfo.NETWORK_ERROR -> { DataError.Uploader.NETWORK_ERROR }
        ErrorInfo.FILE_DOES_NOT_EXIST -> { DataError.Uploader.FILE_DOES_NOT_EXIST }
        else -> {
            DataError.Uploader.UNKNOWN
        }
    }
}