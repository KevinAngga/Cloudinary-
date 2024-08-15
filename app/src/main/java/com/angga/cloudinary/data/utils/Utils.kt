package com.angga.cloudinary.data.utils

import com.angga.cloudinary.data.utils.Constant.ALLOWED_CHAR
import com.angga.cloudinary.data.utils.Constant.SEPARATOR

fun generateRandomPublicId(length: Int = 16): String {
    val allowedChars = ALLOWED_CHAR
    return (1..length)
        .map { allowedChars.random() }
        .joinToString(SEPARATOR).trim()
}