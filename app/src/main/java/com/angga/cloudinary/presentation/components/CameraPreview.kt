package com.angga.cloudinary.presentation.components

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.angga.cloudinary.presentation.ui.theme.CloudinaryTheme

@Composable
fun CameraPreview(
    controller: LifecycleCameraController?,
    modifier: Modifier = Modifier,
    isRecording : Boolean = false,
    onClick : () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        val lifeCycleOwner = LocalLifecycleOwner.current
        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    this.controller = controller
                    controller?.bindToLifecycle(lifeCycleOwner)
                }
            },
            modifier = modifier
        )


        CameraFloatingActionButton(
            modifier = Modifier.padding(bottom = 24.dp),
            icon = if (isRecording) StopIcon else PlayIcon,
            onClick = { onClick() }
        )
    }
}

@Preview
@Composable
fun CameraPreviewPrev() {
    CloudinaryTheme {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            controller = null,
            onClick = {}
        )
    }
}