package com.angga.cloudinary.di

import android.content.Context
import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.data.repository.NetworkRepositoryImpl
import com.angga.cloudinary.data.repository.VideoCaptureRepositoryImpl
import com.angga.cloudinary.domain.repository.NetworkRepository
import com.angga.cloudinary.domain.repository.VideoCaptureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkState(
        @ApplicationContext context: Context
    ): NetworkRepository = NetworkRepositoryImpl(
        context = context
    )

    @Provides
    @Singleton
    fun provideVideoCaptureRepository(
        networkRepository: NetworkRepository
    ): VideoCaptureRepository  {
        return VideoCaptureRepositoryImpl(
            networkRepository = networkRepository
        )
    }

}