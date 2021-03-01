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
package com.rin.compose.kitty.ui.popular.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meowbase.toolkit.lifecycle.getValue
import com.meowbase.toolkit.lifecycle.setValue
import com.rin.compose.kitty.data.Cat
import com.rin.compose.kitty.data.PopularVideo
import com.rin.compose.kitty.data.PopularVideo.Companion.named
import com.rin.compose.kitty.repository.PopularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 用于管理网红猫咪图库的数据
 * ViewModel for popular cats gallery.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class GalleryViewModel @Inject constructor(
  private val repository: PopularRepository
) : ViewModel() {
  val cats = MutableLiveData<Array<Cat>>()
  val peekDetails = MutableLiveData<Details>()

  /**
   * 准备网红猫咪们的数据
   * Prepare the data of the web celebrity cats.
   */
  fun preparePopularCats(country: String) {
    cats.value = when (country) {
      named<PopularVideo.China>() -> repository.chinaCats
      named<PopularVideo.America>() -> repository.americaCats
      named<PopularVideo.Britain>() -> repository.chinaCats
      named<PopularVideo.Korea>() -> repository.chinaCats
      named<PopularVideo.Russia>() -> repository.chinaCats
      named<PopularVideo.Sweden>() -> repository.chinaCats
      else -> error("No cat star is recorded in this country!")
    }
  }
}
