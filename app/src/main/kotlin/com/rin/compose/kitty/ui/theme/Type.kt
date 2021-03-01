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
package com.rin.compose.kitty.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rin.compose.kitty.R

val typography = Typography(
  h1 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 90.sp,
    letterSpacing = 3.6.sp,
  ),
  h2 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 68.sp,
  ),
  h3 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 40.sp,
  ),
  h4 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 30.sp,
    letterSpacing = 1.sp,
  ),
  body1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
)

object KittyFont {
  val Impact = FontFamily(Font(R.font.impact))
  val HYZhuZiMeiXinTiW = FontFamily(Font(R.font.hy_zhuzimeixinti_w))
}
