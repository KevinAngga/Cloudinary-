package com.angga.cloudinary.presentation.ui.video

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.angga.cloudinary.R
import com.angga.cloudinary.presentation.components.CameraPreview
import com.angga.cloudinary.presentation.components.DeniedPermission
import com.angga.cloudinary.presentation.components.FlipCameraIcon
import com.angga.cloudinary.presentation.components.UploadLoadingDialog
import com.angga.cloudinary.presentation.ui.theme.CloudinaryTheme
import com.angga.cloudinary.presentation.ui.utils.ObserveAsEvents

@Composable
fun VideoScreenRoot(viewModel: VideoViewModel = hiltViewModel()) {

    //for create a toast we need a context, the context return here are from the parent
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is VideoEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            VideoEvent.Success -> {
                Toast.makeText(
                    context,R.string.upload_video_done,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

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
    onAction: (VideoAction) -> Unit,
) {
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { grantedMap ->
            val allGranted = grantedMap.values.all { it }
            if (allGranted) {
                // Permission is granted
                onAction(VideoAction.OnCheckPermission(hasAllPermission = true))
            } else {
                // Permission is denied
                onAction(VideoAction.OnCheckPermission(hasAllPermission = false))
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

        if (!state.hasAllPermission) {
            DeniedPermission(modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1.6f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            CameraPreview(
                controller = cameraController,
                modifier = Modifier
                    .fillMaxSize(),
                isRecording = state.isRecording,
                onClick = {
                    onAction(VideoAction.OnRecordClick)
                }
            )
        }

        if (state.isUploading) {
            UploadLoadingDialog(
                cancelButton = {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onAction(VideoAction.OnStopClick) }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Button(
                    enabled = state.videoUri != null,
                    onClick = { onAction(VideoAction.OnUploadClick) }
                ) {
                    Text(stringResource(id = R.string.upload_video))
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { onAction(VideoAction.OnChangeCameraClick) }) {
                    Icon(
                        imageVector = FlipCameraIcon,
                        contentDescription = "flip"
                    )
                }
            }
        }
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