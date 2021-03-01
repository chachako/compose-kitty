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
@file:Suppress("NOTHING_TO_INLINE")

package com.rin.compose.kitty.util

import android.content.res.AssetManager
import android.net.Uri

lateinit var assetManager: AssetManager

inline fun getAsset(path: String): Uri = Uri.parse("file:///android_asset/$path")

inline fun getAssetsWithPrefix(folder: String, prefix: String): List<Uri> =
  assetManager.list(folder)
    ?.filter { it.startsWith(prefix) }
    ?.map { getAsset("$folder/$it") }
    ?: emptyList()
