/*
 * Copyright 2021 The Android Open Source Project
 * Copyright 2021 RinOrz (凛)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Github home page: https://github.com/RinOrz
 */
package com.rin.compose.kitty

import android.app.Application
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
