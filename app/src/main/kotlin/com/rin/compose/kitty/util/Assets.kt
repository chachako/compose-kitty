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