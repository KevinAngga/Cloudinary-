package com.angga.cloudinary.domain.utils

import com.angga.cloudinary.R
import com.angga.cloudinary.presentation.ui.utils.UiText

fun DataError.asUiText() : UiText {
    return when(this) {
        DataError.Uploader.NETWORK_ERROR -> {
            UiText.StringResource(R.string.error_no_internet)
        }

        DataError.Uploader.FILE_DOES_NOT_EXIST -> {
            UiText.StringResource(R.string.error_file_not_exist)
        }

        DataError.Camera.FILE_SIZE_LIMIT_REACHED -> {
            UiText.StringResource(R.string.error_files_size)
        }


        DataError.Camera.INSUFFICIENT_STORAGE -> {
            UiText.StringResource(R.string.error_insufficient_storage)
        }


        DataError.Camera.INVALID_OUTPUT_OPTIONS -> {
            UiText.StringResource(R.string.error_invalid_output)
        }

        DataError.Camera.ENCODING_FAILED -> {
            UiText.StringResource(R.string.error_encoding_failed)
        }


        DataError.Camera.RECORDER_ERROR -> {
            UiText.StringResource(R.string.error_recorder_error)
        }

        DataError.Camera.NO_VALID_DATA -> {
            UiText.StringResource(R.string.error_no_valid_data)
        }

        DataError.Camera.SOURCE_INACTIVE -> {
            UiText.StringResource(R.string.error_source_inactive)
        }

        else -> {
            UiText.StringResource(R.string.error_unknown)
        }
    }
}