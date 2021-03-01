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

import androidx.annotation.DrawableRes
import com.rin.compose.kitty.R

/**
 * 储存了所有 UI 屏幕的数据
 * Store all UI screen data.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
sealed class Screen(val route: String, @DrawableRes val icon: Int = 0) {
  object Popular : Screen("Popular", R.drawable.ic_popular) {
    object Gallery : Screen("Gallery/{country}") {
      fun route(country: String) = "Gallery/$country"
    }
    object Details : Screen("Details/{country}/{name}") {
      fun route(country: String, name: String) = "Details/$country/$name"
    }
  }
  object Explore : Screen("Explore", R.drawable.ic_explore)
  object Favorite : Screen("Favorite", R.drawable.ic_favorite)
  object Profile : Screen("Profile")
}
