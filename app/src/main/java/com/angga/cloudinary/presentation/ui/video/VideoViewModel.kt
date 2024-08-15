package com.angga.cloudinary.presentation.ui.video

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.domain.repository.VideoCaptureRepository
import com.angga.cloudinary.domain.utils.Result
import com.angga.cloudinary.domain.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val dataSource: CameraDataSource,
    private val videoUploadRepository: VideoCaptureRepository,
) : ViewModel() {

    var state by mutableStateOf(VideoState())
        private set

    private val eventChanel = Channel<VideoEvent>()
    val events = eventChanel.receiveAsFlow()

    val cameraController = dataSource.getCameraController()

    fun onAction(action: VideoAction) {
        when (action) {
            VideoAction.OnRecordClick -> { if (state.isRecording) dataSource.stopRecording() else captureVideo() }
            VideoAction.OnStopClick -> { stopUploadVideo() }
            VideoAction.OnUploadClick -> { uploadVideo() }
            VideoAction.OnChangeCameraClick -> { changeCamera() }
            is VideoAction.OnCheckPermission -> {
                state = state.copy(
                    hasAllPermission = action.hasAllPermission
                )
            }
        }
    }

    private fun clearUpState() {
        state = state.copy(
            videoUri = null,
            showDialog = false,
            isRecording = false,
            isUploading = false,
            isFinishUpload = false
        )
    }

    private fun changeCamera() {
        if (cameraController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    private fun captureVideo() {
        state = state.copy(isRecording = true)
        viewModelScope.launch {
            dataSource.captureVideo().collect { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            videoUri = result.data,
                            isRecording = false
                        )
                    }

                    is Result.Failed -> {
                        state = state.copy(isRecording = false)
                        println("==== error "+result.error.name)
                        eventChanel.send(VideoEvent.Error(result.error.asUiText()))
                    }
                }
            }
        }
    }

    private fun stopUploadVideo() {
        state = state.copy(
            isFinishUpload = true,
            isUploading = false
        )
        videoUploadRepository.cancelUpload()
    }

    private fun uploadVideo() {
        state = state.copy(isUploading = true)
        viewModelScope.launch {
            videoUploadRepository.uploadVideo(
                state.videoUri.toString()
            ).collect { result ->
                when (result) {
                    is Result.Failed -> {
                        state = state.copy(isUploading = false)
                        eventChanel.send(VideoEvent.Error(result.error.asUiText()))
                    }

                    is Result.Success -> {
                        state = state.copy(
                            isUploading = false,
                            isFinishUpload = true
                        )
                        clearUpState()
                        eventChanel.send(VideoEvent.Success)
                    }
                }
            }
        }
    }

}