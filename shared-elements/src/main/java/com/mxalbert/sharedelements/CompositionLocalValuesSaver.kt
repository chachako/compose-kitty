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

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*

@Suppress("UNCHECKED_CAST")
private val compositionLocalList = listOf(
  LocalAbsoluteElevation,
  LocalContentColor,
  LocalContentAlpha,
  LocalIndication,
  LocalTextSelectionColors,
  LocalTextStyle
) as List<ProvidableCompositionLocal<Any>>

internal inline class CompositionLocalValues(private val values: Array<ProvidedValue<*>>) {

  @Suppress("ComposableNaming")
  @Composable
  @NonRestartableComposable
  fun provided(block: @Composable () -> Unit) {
    CompositionLocalProvider(*values, content = block)
  }
}

internal val compositionLocalValues: CompositionLocalValues
  @Composable get() = CompositionLocalValues(
    compositionLocalList.map { it provides it.current }.toTypedArray()
  )
