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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.util.packFloats
import androidx.compose.ui.util.unpackFloat1
import androidx.compose.ui.util.unpackFloat2

@Immutable
inline class ProgressThresholds(private val packedValue: Long) {

  @Stable
  val start: Float
    get() = unpackFloat1(packedValue)

  @Stable
  val end: Float
    get() = unpackFloat2(packedValue)

  @Suppress("NOTHING_TO_INLINE")
  @Stable
  inline operator fun component1(): Float = start

  @Suppress("NOTHING_TO_INLINE")
  @Stable
  inline operator fun component2(): Float = end
}

@Stable
fun ProgressThresholds(start: Float, end: Float) = ProgressThresholds(packFloats(start, end))

@Stable
internal fun ProgressThresholds.applyTo(fraction: Float): Float = when {
  fraction < start -> 0f
  fraction in start..end -> (fraction - start) / (end - start)
  else -> 1f
}
