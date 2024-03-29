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
import androidx.compose.ui.util.packFloats

internal object QuadraticBezier {

  private class PointEntry(
    @JvmField val t: Float,
    @JvmField val point: Offset
  ) {
    @JvmField
    var next: PointEntry? = null
  }

  private fun calculate(t: Float, p0: Float, p1: Float, p2: Float): Float {
    val oneMinusT = 1 - t
    return oneMinusT * (oneMinusT * p0 + t * p1) + t * (oneMinusT * p1 + t * p2)
  }

  private fun coordinate(t: Float, p0: Offset, p1: Offset, p2: Offset): Offset =
    Offset(
      calculate(t, p0.x, p1.x, p2.x),
      calculate(t, p0.y, p1.y, p2.y)
    )

  fun approximate(
    p0: Offset,
    p1: Offset,
    p2: Offset,
    acceptableError: Float
  ): Pair<FloatArray, LongArray> {
    val errorSquared = acceptableError * acceptableError

    val start = PointEntry(0f, coordinate(0f, p0, p1, p2))
    var cur = start
    var next = PointEntry(1f, coordinate(1f, p0, p1, p2))
    start.next = next
    var count = 2
    while (true) {
      var needsSubdivision: Boolean
      do {
        val midT = (cur.t + next.t) / 2
        val midX = (cur.point.x + next.point.x) / 2
        val midY = (cur.point.y + next.point.y) / 2

        val midPoint = coordinate(midT, p0, p1, p2)
        val xError = midPoint.x - midX
        val yError = midPoint.y - midY
        val midErrorSquared = (xError * xError) + (yError * yError)
        needsSubdivision = midErrorSquared > errorSquared

        if (needsSubdivision) {
          val new = PointEntry(midT, midPoint)
          cur.next = new
          new.next = next
          next = new
          count++
        }
      } while (needsSubdivision)
      cur = next
      next = cur.next ?: break
    }

    cur = start
    var length = 0f
    var last = Offset.Unspecified
    val result = LongArray(count)
    val lengths = FloatArray(count)
    for (i in result.indices) {
      val point = cur.point
      result[i] = packFloats(point.x, point.y)
      if (i > 0) {
        val distance = (point - last).getDistance()
        length += distance
        lengths[i] = length
      }
      cur = cur.next ?: break
      last = point
    }

    if (length > 0) {
      for (index in lengths.indices) {
        lengths[index] /= length
      }
    }

    return lengths to result
  }
}
