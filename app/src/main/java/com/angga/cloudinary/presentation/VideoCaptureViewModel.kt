package com.angga.cloudinary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.domain.repository.VideoCaptureRepository
import com.angga.cloudinary.domain.utils.Result
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoCaptureViewModel @Inject constructor(
    private val dataSource: CameraDataSource
) : ViewModel() {

    var state by mutableStateOf(VideoState())
        private set

    val cameraController = dataSource.getCameraController()

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
        MediaManager.get().upload(state.videoUri)
            .unsigned("ol8ytuxv")
            .option("resource_type", "video")
            .option("public_id", "testingaaa")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                    println("==== upload start")
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                    println("==== upload progress")
                }

                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    println("==== upload suscess")
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    println("==== upload error")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    println("==== upload reschedule")
                }

            }).dispatch()
    }

}