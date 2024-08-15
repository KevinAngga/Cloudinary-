package com.angga.cloudinary.di

import android.content.Context
import com.angga.cloudinary.data.camera.CameraDataSource
import com.angga.cloudinary.data.repository.NetworkRepositoryImpl
import com.angga.cloudinary.domain.repository.NetworkRepository
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
    fun provideCameraDataSource(
        @ApplicationContext context: Context
    ): CameraDataSource = CameraDataSource(
        context = context
    )
}