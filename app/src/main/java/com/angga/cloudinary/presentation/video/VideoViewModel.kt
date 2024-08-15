package com.angga.cloudinary.presentation.video

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.domain.repository.VideoCaptureRepository
import com.angga.cloudinary.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val dataSource: CameraDataSource,
    private val videoUploadRepository: VideoCaptureRepository
) : ViewModel() {

    var state by mutableStateOf(VideoState())
        private set

    val cameraController = dataSource.getCameraController()

    fun onAction(action: VideoAction) {
        when(action) {
            VideoAction.OnRecordClick -> { captureVideo() }
            VideoAction.OnStopClick -> { }
            VideoAction.OnUploadClick -> { upload() }
        }
    }

    fun captureVideo() {
        viewModelScope.launch {
            dataSource.captureVideo().collect { result ->
                when(result) {
                    is Result.Success -> {
                        state = state.copy(
                            videoUri = result.data
                        )
                    }

                    is Result.Failed -> {
                        println(result.error.name)
                    }
                }
            }
        }
    }

    fun upload() {
        viewModelScope.launch {
            videoUploadRepository.uploadVideo(
                state.videoUri.toString()
            ).collect { result ->
                when(result) {
                    is Result.Failed -> { println("=== angga error "+result.error.name) }
                    is Result.Success -> {
                        println("==== success "+result.data)
                    }
                }
            }
        }
    }

}