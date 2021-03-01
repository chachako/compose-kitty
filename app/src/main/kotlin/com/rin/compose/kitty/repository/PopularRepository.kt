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
@file:Suppress("SpellCheckingInspection")

package com.rin.compose.kitty.repository

import com.rin.compose.kitty.data.Cat
import com.rin.compose.kitty.data.Gender
import com.rin.compose.kitty.data.PopularVideo
import com.rin.compose.kitty.data.PopularVideo.Companion.named
import com.rin.compose.kitty.util.getAssetsWithPrefix
import javax.inject.Inject

/**
 * 提供热门屏幕所需要的数据
 * Provides data to popular screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
class PopularRepository @Inject constructor() {

  /**
   * 返回了解的网红猫所在国家
   * Returns country where the cat stars.
   */
  val countries: Array<String> by lazy {
    arrayOf(
      named<PopularVideo.China>(),
      named<PopularVideo.America>(),
      named<PopularVideo.Russia>(),
      named<PopularVideo.Korea>(),
      named<PopularVideo.Sweden>(),
      named<PopularVideo.Britain>(),
    )
  }

  /**
   * 返回中国的猫咪视频
   * Returns kitty videos from China.
   */
  val chinaVideos: Array<PopularVideo> by lazy {
    arrayOf(
      PopularVideo.China("布鲁斯你别闹.mp4"),
      PopularVideo.China("吴kiwi是一只布偶.mp4"),
      PopularVideo.China("一只钝钝少爷.mp4"),
      PopularVideo.China("无敌噼啪猫.mp4"),
      PopularVideo.China("白歌Ws.mp4"),
    )
  }

  /**
   * 返回美国的猫咪视频
   * Returns kitty videos from America.
   */
  val americaVideos: Array<PopularVideo> by lazy {
    arrayOf(
      PopularVideo.America("Boomba.mp4"),
      PopularVideo.America("Nala.mp4"),
    )
  }

  /**
   * 返回韩国的猫咪视频
   * Returns kitty videos from Korea.
   */
  val koreaVideos: Array<PopularVideo> by lazy {
    arrayOf(
      PopularVideo.Korea("nurseukieeeee.mp4"),
    )
  }

  /**
   * 返回英国的猫咪视频
   * Returns kitty videos from Russia.
   */
  val britainVideos: Array<PopularVideo> by lazy {
    arrayOf(
      PopularVideo.Britain("coolestpotatoe.mp4"),
    )
  }

  /**
   * 返回俄罗斯的猫咪视频
   * Returns kitty videos from Russia.
   */
  val russiaVideos: Array<PopularVideo> by lazy {
    arrayOf(
      PopularVideo.Russia("little_shine_cat.mp4"),
      PopularVideo.Russia("hosico_cat.mp4"),
      PopularVideo.Russia("missa_cat8.mp4"),
    )
  }

  /**
   * 返回瑞典的猫咪视频
   * Returns kitty videos from Sweden.
   */
  val swedenVideos: Array<PopularVideo> by lazy {
    arrayOf(
      PopularVideo.Sweden("Aurorapurr.mp4"),
    )
  }

  val chinaCats by lazy {
    arrayOf(
      Cat(
        name = "Kiwi",
        gender = Gender.Female,
        pictures = listPictures("china/吴Kiwi"),
      ),
      Cat(
        name = "酥饼大人",
        pictures = listPictures("china/酥饼大人"),
      ),
      Cat(
        name = "泡芙",
        pictures = listPictures("china/泡芙"),
      ),
      Cat(
        name = "狐狸",
        pictures = listPictures("china/狐狸"),
      ),
      Cat(
        name = "馒头",
        pictures = listPictures("china/馒头"),
      ),
    )
  }

  val americaCats by lazy {
    arrayOf(
      Cat(
        name = "Brimley",
        pictures = listPictures("america/brimleycat"),
      ),
      Cat(
        name = "Waffles",
        pictures = listPictures("america/waffles"),
      ),
      Cat(
        name = "Albert Baby",
        gender = Gender.Female,
        pictures = listPictures("america/albertbabycat"),
      ),
      Cat(
        name = "Lil BUB",
        pictures = listPictures("america/lilbub"),
      ),
      Cat(
        name = "Sunglass",
        pictures = listPictures("america/sunglasscat"),
      ),
    )
  }

  private fun listPictures(path: String) = getAssetsWithPrefix(path, "img")
}
