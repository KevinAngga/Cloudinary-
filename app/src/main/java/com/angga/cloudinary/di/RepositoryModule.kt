package com.angga.cloudinary.di

import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.data.repository.VideoCaptureRepositoryImpl
import com.angga.cloudinary.domain.repository.VideoCaptureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideVideoCaptureRepository(): VideoCaptureRepository  {
        return VideoCaptureRepositoryImpl()
    }

}