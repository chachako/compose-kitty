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

/**
 * 储存了来自不同国家的猫咪视频
 * Store popular cat videos from different countries.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
sealed class PopularVideo(val assetsPath: String) {
  class China(fileName: String) : PopularVideo("china/$fileName")
  class America(fileName: String) : PopularVideo("america/$fileName")
  class Korea(fileName: String) : PopularVideo("korea/$fileName")
  class Britain(fileName: String) : PopularVideo("britain/$fileName")
  class Russia(fileName: String) : PopularVideo("russia/$fileName")
  class Sweden(fileName: String) : PopularVideo("sweden/$fileName")

  companion object {
    inline fun <reified T : PopularVideo> named() = T::class.java.simpleName
  }
}
