package com.nidhi.recipevault
import android.app.Application
import android.util.Log
import com.nidhi.recipevault.utils.LogUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RecipeVaultApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(LogUtils.getTag(this::class.java), "Application class is creating")
    }
}