package com.angga.cloudinary.di

import android.content.Context
import com.angga.cloudinary.data.camera.AndroidCameraControllerImpl
import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.domain.repository.AndroidCameraController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAndroidCameraController(
        @ApplicationContext context: Context
    ): AndroidCameraController = AndroidCameraControllerImpl(context)

    @Provides
    @Singleton
    fun provideCameraDataSource(
        @ApplicationContext context: Context
    ): CameraDataSource = CameraDataSource(
        context = context
    )
}