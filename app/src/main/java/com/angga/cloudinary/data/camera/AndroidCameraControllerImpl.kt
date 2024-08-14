package com.angga.cloudinary.data.camera

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.core.content.ContextCompat
import com.angga.cloudinary.domain.utils.DataError
import com.angga.cloudinary.domain.utils.Result
import com.angga.cloudinary.data.utils.handleCameraError
import com.angga.cloudinary.domain.model.Video
import com.angga.cloudinary.domain.repository.AndroidCameraController
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import javax.inject.Inject

class AndroidCameraControllerImpl @Inject constructor(
    private val context: Context,
) : AndroidCameraController {
    private val cameraController = LifecycleCameraController(context)

    val outputFile = File(context.filesDir, "video.mp4")

    val outputOptions = FileOutputOptions.Builder(outputFile).build()

    private var recording: Recording? = null

    @SuppressLint("MissingPermission")
    override fun captureVideo(): Flow<Result<Video, DataError.Camera>> = callbackFlow {
        cameraController.setEnabledUseCases(CameraController.VIDEO_CAPTURE)

        if (recording != null) {
            recording?.stop()
            recording = null
        }

        recording = cameraController.startRecording(
            outputOptions,
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(context)
        ) { event ->
            when (event) {
                is VideoRecordEvent.Finalize -> {
                    if (event.hasError()) {
                        recording?.close()
                        trySend(Result.Failed(handleCameraError(event.error)))
                    } else {
                        trySend(Result.Success(Video(filePath = event.outputResults.outputUri.toString())))
                    }

                    close()
                }
            }
        }

        // Await close from the caller side
        awaitClose {
            recording?.close()
        }

    }

    override fun stopRecording() {
        recording?.stop()
        recording?.close()
    }


}
