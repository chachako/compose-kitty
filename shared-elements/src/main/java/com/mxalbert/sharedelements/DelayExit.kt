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

import androidx.compose.runtime.*
import com.mxalbert.sharedelements.DelayExitState.*

/**
 * When [visible] becomes false, if transition is running, delay the exit of the content until
 * transition finishes. Note that you may need to call [SharedElementsRootScope.prepareTransition]
 * before [visible] becomes false to start transition immediately.
 */
@Composable
fun SharedElementsRootScope.DelayExit(
  visible: Boolean,
  content: @Composable () -> Unit
) {
  var state by remember { mutableStateOf(Invisible) }

  when (state) {
    Invisible -> {
      if (visible) state = Visible
    }
    Visible -> {
      if (!visible) {
        state = if (isRunningTransition) ExitDelayed else Invisible
      }
    }
    ExitDelayed -> {
      if (!isRunningTransition) state = Invisible
    }
  }

  if (state != Invisible) content()
}

private enum class DelayExitState {
  Invisible, Visible, ExitDelayed
}
