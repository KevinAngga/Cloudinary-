package com.angga.cloudinary.domain.utils

sealed interface DataError : Error {
    enum class Camera : DataError {
        UNKNOWN,
        FILE_SIZE_LIMIT_REACHED,
        INSUFFICIENT_STORAGE,
        INVALID_OUTPUT_OPTIONS,
        ENCODING_FAILED,
        RECORDER_ERROR,
        NO_VALID_DATA,
        SOURCE_INACTIVE
    }


    enum class Uploader : DataError {
        UNKNOWN,
        NETWORK_ERROR,
        FILE_DOES_NOT_EXIST,
        URI_DOES_NOT_EXIST
    }
}