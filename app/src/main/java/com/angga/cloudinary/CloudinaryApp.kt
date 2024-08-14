package com.angga.cloudinary

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CloudinaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = hashMapOf(Pair("cloud_name", "dquttfjsv"))
        MediaManager.init(this, config)
    }
}