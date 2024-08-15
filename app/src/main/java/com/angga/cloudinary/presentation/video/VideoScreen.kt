package com.angga.cloudinary.presentation.video

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.angga.cloudinary.presentation.components.CameraPreview
import com.angga.cloudinary.presentation.ui.theme.CloudinaryTheme

@Composable
fun VideoScreenRoot(viewModel : VideoViewModel = hiltViewModel()) {
    VideoScreen(
        state = viewModel.state,
        cameraController = viewModel.cameraController,
        onAction = viewModel::onAction
    )
}

@Composable
private fun VideoScreen(
    state: VideoState,
    cameraController: LifecycleCameraController?,
    onAction: (VideoAction) -> Unit
) {
    val context = LocalContext.current

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { grantedMap ->
            val allGranted = grantedMap.values.all { it }
            if (allGranted) {
                // Permission is granted
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                // Permission is denied
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }

    LaunchedEffect(key1 = true) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CameraPreview(
            controller = cameraController,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )

        Row(Modifier.fillMaxWidth()) {
            Button(onClick = { onAction(VideoAction.OnRecordClick) }) {
                Text("Capture Video")
            }

            Button(onClick = { onAction(VideoAction.OnUploadClick) }) {
                Text("Upload Video")
            }
        }

        Text("Video captured: ${state.videoUri}")
    }
}

@Preview
@Composable
private fun VideoScreenPreview() {
    CloudinaryTheme {
        VideoScreen(
            state = VideoState(),
            cameraController = null,
            onAction = {}
        )
    }
}