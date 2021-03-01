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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import kotlin.math.max
import kotlin.math.min

@Composable
internal fun ElementContainer(
  modifier: Modifier,
  relaxMaxSize: Boolean = false,
  content: @Composable () -> Unit
) {
  Layout(content, modifier) { measurables, constraints ->
    if (measurables.size > 1) {
      throw IllegalStateException("SharedElement can have only one direct measurable child!")
    }
    val placeable = measurables.firstOrNull()?.measure(
      Constraints(
        minWidth = 0,
        minHeight = 0,
        maxWidth = if (relaxMaxSize) Constraints.Infinity else constraints.maxWidth,
        maxHeight = if (relaxMaxSize) Constraints.Infinity else constraints.maxHeight
      )
    )
    val width = min(max(constraints.minWidth, placeable?.width ?: 0), constraints.maxWidth)
    val height = min(max(constraints.minHeight, placeable?.height ?: 0), constraints.maxHeight)
    layout(width, height) {
      placeable?.place(0, 0)
    }
  }
}
