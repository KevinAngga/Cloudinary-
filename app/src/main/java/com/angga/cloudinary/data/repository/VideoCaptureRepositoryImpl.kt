package com.angga.cloudinary.data.repository

import androidx.core.net.toUri
import com.angga.cloudinary.data.utils.Constant.CONNECT_TIMEOUT
import com.angga.cloudinary.data.utils.Constant.PUBLIC_ID
import com.angga.cloudinary.data.utils.Constant.READ_TIMEOUT
import com.angga.cloudinary.data.utils.Constant.RESOURCE_TYPE
import com.angga.cloudinary.data.utils.Constant.RESOURCE_VALUE
import com.angga.cloudinary.data.utils.Constant.UPLOAD_PRESET
import com.angga.cloudinary.data.utils.generateRandomPublicId
import com.angga.cloudinary.data.utils.handleUploaderError
import com.angga.cloudinary.domain.model.UploadProgress
import com.angga.cloudinary.domain.repository.NetworkRepository
import com.angga.cloudinary.domain.repository.VideoCaptureRepository
import com.angga.cloudinary.domain.utils.DataError
import com.angga.cloudinary.domain.utils.NetworkState
import com.angga.cloudinary.domain.utils.Result
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.UploadPolicy
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoCaptureRepositoryImpl @Inject constructor(
    val networkRepository: NetworkRepository,
) : VideoCaptureRepository {
    private val mediaManager: MediaManager = MediaManager.get()
    private var requestUploadId: String? = null

    override fun uploadVideo(videoUri: String): Flow<Result<UploadProgress, DataError.Uploader>> =
        callbackFlow {
            var isNetworkAvailable = true

            val networkJob = launch {
                networkRepository.observeNetworkState().collect { state ->
                    when (state) {
                        is NetworkState.Connected -> {
                            isNetworkAvailable = true
                        }

                        is NetworkState.Disconnected -> {
                            isNetworkAvailable = false
                            cancelUpload()
                            trySend(Result.Failed(DataError.Uploader.NETWORK_ERROR))
                        }
                    }
                }
            }

            if (requestUploadId != null) {
                cancelUpload() // Ensure previous upload is canceled
            }

            //we only check the connection if the connection interrupted the sdk will try automatically
            if (!isNetworkAvailable) {
                trySend(Result.Failed(DataError.Uploader.NETWORK_ERROR))
                close()
                return@callbackFlow
            }

            requestUploadId = mediaManager.upload(videoUri.toUri()) //extension from string to Uri
                .unsigned(UPLOAD_PRESET)
                .policy(
                    UploadPolicy.Builder()
                        .maxRetries(2)
                        .backoffCriteria(3000, UploadPolicy.BackoffPolicy.LINEAR)
                        .build()
                )
                .option(CONNECT_TIMEOUT, 10000)
                .option(READ_TIMEOUT, 10000)
                .option(RESOURCE_TYPE, RESOURCE_VALUE)
                .option(PUBLIC_ID, generateRandomPublicId())
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String?) {
                        //not handled cause we can just update the ui state
                        println("==== start") //minimal logging more easy so read than log :v
                    }

                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                        //not handled cause totalBytes are always 0.0 and there's a bug that when we trySend this
                        println("==== progress")
                    }

                    override fun onSuccess(
                        requestId: String?,
                        resultData: MutableMap<Any?, Any?>?,
                    ) {
                        println("==== success")
                        trySend(Result.Success(UploadProgress(1f))).isSuccess
                        close()
                    }

                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        println("==== error")
                        trySend(Result.Failed(error?.let { handleUploaderError(it) }
                            ?: DataError.Uploader.UNKNOWN))
                        close()
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                        //not handled cause its says can be ignored UploadCallback.java
                    }

                }).dispatch()

            awaitClose {
                networkJob.cancel()
                cancelUpload()
            }
        }

    override fun cancelUpload() {
        requestUploadId?.let {
            mediaManager.cancelRequest(it)
        }
        mediaManager.cancelAllRequests()
        requestUploadId = null
    }

}