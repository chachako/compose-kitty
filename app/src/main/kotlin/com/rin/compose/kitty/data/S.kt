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
package com.rin.compose.kitty.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase

/**
 * Kitty App 的字符串集中储存地
 * Centrally store the string data of the Kitty App.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
object S {
  val searchCatStar
    @Composable get() = get(
      chinese = "搜索网红猫咪",
      english = "Search the web cat stars"
    )

  val searchCatBreed
    @Composable get() = get(
      chinese = "搜索猫咪品种",
      english = "Search cats by breed"
    )

  val searchCat
    @Composable get() = get(
      chinese = "搜索猫咪",
      english = "Search kittens"
    )

  val instagram
    @Composable get() = get(
      chinese = "ins",
      english = "Instagram"
    )

  val twitter
    @Composable get() = get(
      chinese = "推特",
      english = "Twitter"
    )

  val weibo
    @Composable get() = get(
      chinese = "微博",
      english = "Weibo"
    )

  val catStar
    @Composable get() = get(
      chinese = "网红猫",
      english = "Web cat-star"
    )

  val screenIconDesc
    @Composable get() = get(
      chinese = "代表着进入 %S 屏幕的导航图标",
      english = "A navigation icon that allows you to enter the %S screen."
    )

  val catAvatarDesc
    @Composable get() = get(
      chinese = "这是一只叫做 %S 的猫咪",
      english = "This cat's name is %S."
    )

  @Composable
  fun getBreed(breed: Breed) = when (breed) {
    Breed.Domestic -> get(
      chinese = "田园猫",
      english = "House Cat"
    )
    Breed.Ragdoll -> get(
      chinese = "布偶猫",
      english = "Ragdoll"
    )
    Breed.Unknown -> get(
      chinese = "未知",
      english = "Unknown"
    )
  }

  /**
   * 根据语言环境获取或创建字符串
   * Get or create a string according by locale.
   */
  @Composable
  private fun get(
    chinese: String,
    english: String
  ): String {
    val locale = Locale.current
    return remember(locale) {
      if (locale.language.toLowerCase(locale) == "zh") chinese else english
    }
  }
}
