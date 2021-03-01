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
package com.mxalbert.sharedelements

import androidx.compose.ui.geometry.Offset

class MaterialArcMotion : KeyframeBasedMotion() {

  override fun getKeyframes(start: Offset, end: Offset): Pair<FloatArray, LongArray> =
    QuadraticBezier.approximate(
      start,
      if (start.y > end.y) Offset(end.x, start.y) else Offset(start.x, end.y),
      end,
      0.5f
    )
}

val MaterialArcMotionFactory: PathMotionFactory = { MaterialArcMotion() }
