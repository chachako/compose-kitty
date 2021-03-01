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

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.*
import com.rin.compose.kitty.data.Cat
import com.rin.compose.kitty.data.S.catAvatarDesc
import com.rin.compose.kitty.data.Screen
import com.rin.compose.kitty.ui.AppViewModel
import com.rin.compose.kitty.ui.theme.KittyFont
import com.rin.compose.kitty.util.isChinese
import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * 具有复杂分组的列表效果
 * The list with complex grouping.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun AppViewModel.List(cats: Array<Cat>, viewModel: GalleryViewModel, transition: TransitionMode) {
  val peekDetails by viewModel.peekDetails.observeAsState()

  SharedElementsRoot {
    val listState = rememberLazyListState()
    Crossfade(
      targetState = peekDetails,
      animationSpec = tween(durationMillis = 400)
    ) { details ->
      when (details) {
        null -> LazyColumn(state = listState) {
          itemsIndexed(cats) { index, cat ->
            val alpha by animateFloatAsState(
              targetValue = when (transition) {
                TransitionMode.Enter -> 1f
                TransitionMode.No, TransitionMode.Exit -> 0f
              },
              animationSpec = tween(400, delayMillis = index * 200)
            )
            Column(modifier = Modifier.alpha(alpha)) {
              HeaderItem(cat)
              CatsItem(cat, this@List, viewModel)
            }
          }
        }
        else -> DetailsScreen(details)
      }
    }
  }
}

@Composable
private fun HeaderItem(cat: Cat) = Row(
  modifier = Modifier.clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = null
  ) {
  },
  verticalAlignment = Alignment.Bottom
) {
  Text(
    text = cat.pictures.size.toString(),
    color = MaterialTheme.colors.secondary,
    style = MaterialTheme.typography.h3,
  )
  Spacer(modifier = Modifier.width(8.dp))

  val isChinese = cat.name.isChinese
  Text(
    text = cat.name,
    modifier = Modifier.padding(bottom = if (isChinese) 8.dp else 4.dp),
    fontFamily = if (isChinese) KittyFont.HYZhuZiMeiXinTiW else KittyFont.Impact,
    color = MaterialTheme.colors.onBackground,
    style = MaterialTheme.typography.h4,
  )
}

private val MaterialFadeInTransitionSpec = MaterialContainerTransformSpec(
  pathMotionFactory = MaterialArcMotionFactory,
  durationMillis = 400,
  fadeMode = FadeMode.In
)

@Composable
private fun LazyItemScope.CatsItem(
  cat: Cat,
  appViewModel: AppViewModel,
  viewModel: GalleryViewModel
) = Row(
  modifier = Modifier
    .padding(top = 24.dp, bottom = 56.dp)
    .fillParentMaxWidth(),
  horizontalArrangement = Arrangement.SpaceBetween
) {
  // 每一行的小猫最多只能显示四个图片
  // TODO: 如果小猫的图片多于四个，我们应该在最后一个 item 上显示 '更多' 图标
  //  If there are more than four cat pictures,
  //  we should display the "more" icon on the last item.
  repeat(4) {
    val mod = Modifier
      .size(72.dp)

    val picture = cat.pictures.getOrNull(it)
    if (picture == null) {
      // 如果不满 4 个 Item，则用 Spacer 来填充它
      Spacer(modifier = mod)
      return@repeat
    }

    SharedMaterialContainer(
      key = picture,
      screenKey = Screen.Popular.Gallery,
      shape = CircleShape,
      color = Color.Transparent,
      transitionSpec = MaterialFadeInTransitionSpec
    ) {
      CoilImage(
        data = picture,
        modifier = mod.clickable {
          appViewModel.hideTitle()
          viewModel.peekDetails.value = Details(cat, it)
        },
        contentScale = ContentScale.Crop,
        contentDescription = catAvatarDesc.format(cat.name),
        loading = { Spacer(Modifier.background(MaterialTheme.colors.onBackground.copy(ContentAlpha.disabled))) },
      )
    }
  }
}
