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
package com.rin.compose.kitty.ui.popular

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.meowbase.toolkit.isNotNull
import com.rin.compose.kitty.data.Screen
import com.rin.compose.kitty.ui.AppViewModel
import com.rin.compose.kitty.ui.Pager
import com.rin.compose.kitty.ui.SearchBarMode
import com.rin.compose.kitty.ui.rememberPagerState

/**
 * 具有纵向自动居中的列表效果
 * The list with vertical automatic centering effect.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun AppViewModel.List(
  countries: Array<String>,
  modifier: Modifier,
  onItemSelected: (country: String) -> Unit
) {
  var navigatedRoute by remember { mutableStateOf<String?>(null) }
  val pagerState = rememberPagerState(pageCount = countries.size)

  Pager(
    state = pagerState,
    orientation = Orientation.Vertical,
    modifier = modifier
      .graphicsLayer(clip = navigatedRoute.isNotNull())
      // 向上移动一些距离，以增加空间美感
      .offset(y = (-62).dp),
    offscreenLimit = countries.size
  ) {
    val country = countries[page]
    val isCurrentPage = currentPage == page
    if (isCurrentPage) onItemSelected(country)

    val scale by animateFloatAsState(if (isCurrentPage) 1f else 0.82f)
    val alpha by animateFloatAsState(
      targetValue = when {
        // 跳转则需要淡出
        navigatedRoute.isNotNull() -> 0f
        else -> if (isCurrentPage) 1f else 0.32f
      },
      animationSpec = spring(2.3f),
      finishedListener = {
        // 最终我们在这里跳转界面
        navigatedRoute?.let(navigator::navigate)
      }
    )
    val offset by animateFloatAsState(
      targetValue = when {
        // 跳转后往上下两边偏移
        navigatedRoute.isNotNull() -> if (isCurrentPage) -450f else 600f
        else -> 0f
      },
      animationSpec = spring(1.6f),
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .graphicsLayer(
          alpha = alpha,
          scaleX = scale,
          scaleY = scale,
          translationY = offset,
          transformOrigin = TransformOrigin(0f, 1f),
        )
        .padding(vertical = 4.dp)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null
        ) {
          navigatedRoute = Screen.Popular.Gallery.route(country)
          searchBarMode = SearchBarMode.Fold
          fadeOutBackground()

          // TODO: Scroll to the corresponding item position after clicking
          // pagerState.scrollToPage()
        },
    ) {
      Text(
        text = country,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h1,
      )
      Spacer(
        modifier = Modifier
          .background(
            color = MaterialTheme.colors.onBackground,
            shape = RoundedCornerShape(
              topStartPercent = 0,
              bottomStartPercent = 0,
              topEndPercent = 50,
              bottomEndPercent = 50,
            )
          )
          .size(width = 86.dp, height = 4.dp)
          .padding(top = 12.dp),
      )
    }
  }
}
