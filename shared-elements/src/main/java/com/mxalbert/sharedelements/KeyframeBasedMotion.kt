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
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.util.unpackFloat1
import androidx.compose.ui.util.unpackFloat2

abstract class KeyframeBasedMotion : PathMotion {

  private var start = Offset.Unspecified
  private var end = Offset.Unspecified
  private var keyframes: Pair<FloatArray, LongArray>? = null

  protected abstract fun getKeyframes(start: Offset, end: Offset): Pair<FloatArray, LongArray>

  private fun LongArray.getOffset(index: Int) = get(index).let {
    Offset(unpackFloat1(it), unpackFloat2(it))
  }

  override fun invoke(start: Offset, end: Offset, fraction: Float): Offset {
    var frac = fraction
    if (start != this.start || end != this.end) {
      if (start == this.end && end == this.start) {
        frac = 1 - frac
      } else {
        keyframes = null
        this.start = start
        this.end = end
      }
    }
    val (fractions, offsets) = keyframes ?: getKeyframes(start, end).also { keyframes = it }
    val count = fractions.size

    return when {
      frac < 0f -> interpolateInRange(fractions, offsets, frac, 0, 1)
      frac > 1f -> interpolateInRange(fractions, offsets, frac, count - 2, count - 1)
      frac == 0f -> offsets.getOffset(0)
      frac == 1f -> offsets.getOffset(count - 1)
      else -> {
        // Binary search for the correct section
        var low = 0
        var high = count - 1
        while (low <= high) {
          val mid = (low + high) / 2
          val midFraction = fractions[mid]

          when {
            frac < midFraction -> high = mid - 1
            frac > midFraction -> low = mid + 1
            else -> return offsets.getOffset(mid)
          }
        }

        // now high is below the fraction and low is above the fraction
        interpolateInRange(fractions, offsets, frac, high, low)
      }
    }
  }

  private fun interpolateInRange(
    fractions: FloatArray,
    offsets: LongArray,
    fraction: Float,
    startIndex: Int,
    endIndex: Int
  ): Offset {
    val startFraction = fractions[startIndex]
    val endFraction = fractions[endIndex]
    val intervalFraction = (fraction - startFraction) / (endFraction - startFraction)
    val start = offsets.getOffset(startIndex)
    val end = offsets.getOffset(endIndex)
    return lerp(start, end, intervalFraction)
  }
}
