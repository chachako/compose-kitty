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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp

internal val Rect.area: Float
  get() = width * height

internal operator fun Size.div(operand: Size): ScaleFactor =
  ScaleFactor(width / operand.width, height / operand.height)

internal fun calculateDirection(start: Rect, end: Rect): TransitionDirection =
  if (end.area > start.area) TransitionDirection.Enter else TransitionDirection.Return

internal fun calculateAlpha(
  direction: TransitionDirection?,
  fadeMode: FadeMode?,
  fraction: Float, // Absolute
  isStart: Boolean
) = when (fadeMode) {
  FadeMode.In, null -> if (isStart) 1f else fraction
  FadeMode.Out -> if (isStart) 1 - fraction else 1f
  FadeMode.Cross -> if (isStart) 1 - fraction else fraction
  FadeMode.Through -> {
    val threshold = if (direction == TransitionDirection.Enter)
      FadeThroughProgressThreshold else 1 - FadeThroughProgressThreshold
    if (fraction < threshold) {
      if (isStart) 1 - fraction / threshold else 0f
    } else {
      if (isStart) 0f else (fraction - threshold) / (1 - threshold)
    }
  }
}

internal fun calculateOffset(
  start: Rect,
  end: Rect?,
  fraction: Float, // Relative
  pathMotion: PathMotion?,
  width: Float
): Offset = if (end == null) start.topLeft else {
  val topCenter = pathMotion!!.invoke(
    start.topCenter,
    end.topCenter,
    fraction
  )
  Offset(topCenter.x - width / 2, topCenter.y)
}

private val Identity = ScaleFactor(1f, 1f)

internal fun calculateScale(
  start: Rect,
  end: Rect?,
  fraction: Float // Relative
): ScaleFactor =
  if (end == null) Identity else lerp(Identity, end.size / start.size, fraction)
