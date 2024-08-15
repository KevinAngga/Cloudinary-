package com.angga.cloudinary.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.angga.cloudinary.R

val PlayIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.play)

val StopIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.stop)

val FlipCameraIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.flip_camera)

val UploadIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.upload)
