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
package com.rin.compose.kitty.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rin.compose.kitty.data.PopularVideo
import com.rin.compose.kitty.data.PopularVideo.Companion.named
import com.rin.compose.kitty.repository.PopularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 用于管理热门屏幕的数据
 * ViewModel for popular screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class PopularViewModel @Inject constructor(
  private val repository: PopularRepository
) : ViewModel() {
  val countries = MutableLiveData(repository.countries)

  val videos = MutableLiveData(repository.chinaVideos)

  fun changeVideos(country: String) {
    val videos = when (country) {
      named<PopularVideo.China>() -> repository.chinaVideos
      named<PopularVideo.America>() -> repository.americaVideos
      named<PopularVideo.Britain>() -> repository.britainVideos
      named<PopularVideo.Korea>() -> repository.koreaVideos
      named<PopularVideo.Russia>() -> repository.russiaVideos
      named<PopularVideo.Sweden>() -> repository.swedenVideos
      else -> null
    }
    videos?.let { this.videos.value = it }
  }
}
