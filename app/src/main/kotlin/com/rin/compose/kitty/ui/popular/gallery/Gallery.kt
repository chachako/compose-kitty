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
package com.rin.compose.kitty.ui.popular.gallery

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rin.compose.kitty.ui.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppViewModel.Gallery(country: String, viewModel: GalleryViewModel) {
  val scope = rememberCoroutineScope()
  var transition by remember { mutableStateOf(TransitionMode.No) }
  val cats by viewModel.cats.observeAsState()

  Title {
    Text(
      text = country,
      modifier = Modifier
        .animTitle(transition)
        .align(Alignment.CenterStart)
        // 避免与搜索栏重叠
        .padding(end = 54.dp),
      fontSize = 56.sp,
      color = MaterialTheme.colors.onBackground,
      style = MaterialTheme.typography.h1
    )
  }

  cats?.let { List(it, viewModel, transition) }

  LaunchedEffect(country) { viewModel.preparePopularCats(country) }

  DisposableEffect(Unit) {
    scope.launch {
      // FIXME (Maybe a compose bug):
      //  稍微延迟后再播放动画，否则动画将无法播放，会出现抢帧情况
      //  Play the animation after a slight delay,
      //  Otherwise the animation will be completed directly.
      delay(5)
      transition = TransitionMode.Enter
    }
    onDispose {
      transition = TransitionMode.Exit
    }
  }
}

private fun Modifier.animTitle(transition: TransitionMode): Modifier = composed {
  val offset by animateFloatAsState(
    targetValue = when (transition) {
      TransitionMode.No -> 100f
      TransitionMode.Enter -> 0f
      TransitionMode.Exit -> 400f
    },
    animationSpec = spring(4f)
  )

  val alpha by animateFloatAsState(
    targetValue = when (transition) {
      TransitionMode.Enter -> 1f
      TransitionMode.No, TransitionMode.Exit -> 0f
    },
    animationSpec = tween(400)
  )

  this.graphicsLayer(
    alpha = alpha,
    translationY = offset
  )
}

enum class TransitionMode {
  Enter, Exit, No
}
