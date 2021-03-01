package com.rin.compose.kitty

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.rin.compose.kitty.util.assetManager
import dagger.hilt.android.HiltAndroidApp

/**
 * Kitty 程序的唯一的入口点
 * The only entry point to the Kitty application.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltAndroidApp
class KittyApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    assetManager = this.assets
  }
}