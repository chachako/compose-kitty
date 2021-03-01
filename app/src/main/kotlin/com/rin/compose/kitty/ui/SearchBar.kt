package com.rin.compose.kitty.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

/**
 * 显示在多个屏幕上的搜索栏
 * Search bar displayed on multiple screens.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun SearchBar(mode: SearchBarMode, text: String?) {
  val fraction by animateFloatAsState(
    targetValue = when (mode) {
      SearchBarMode.Default -> 1f
      SearchBarMode.Fold -> 0f
    },
    animationSpec = spring(4.5f)
  )

  val horizontalPadding by animateDpAsState(
    targetValue = when (mode) {
      SearchBarMode.Default -> 16.dp
      SearchBarMode.Fold -> 8.dp
    },
    animationSpec = tween(delayMillis = 100, easing = LinearEasing)
  )

  AnimatedVisibility(visible = text != null) {
    Button(
      onClick = { /*TODO*/ },
      modifier = Modifier
        .heightIn(min = 56.dp)
        .widthIn(min = 56.dp)
        .fillMaxWidth(fraction),
      colors = ButtonDefaults.buttonColors(
        MaterialTheme.colors.onBackground.copy(alpha = 0.08f),
        MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
      ),
      elevation = null,
      shape = MaterialTheme.shapes.large,
      contentPadding = PaddingValues(
        horizontal = horizontalPadding,
        vertical = 8.dp
      ),
    ) {
      AnimatedVisibility(
        mode == SearchBarMode.Default,
        exit = shrinkHorizontally(Alignment.Start),
        modifier = Modifier
          .weight(1f)
          .alpha(fraction)
          .padding(start = 6.dp, end = 16.dp)
      ) {
        Text(
          text!!,
          maxLines = 1,
          style = MaterialTheme.typography.subtitle1
        )
      }
      Icon(
        Icons.Filled.Search,
        contentDescription = text,
        modifier = Modifier.size(32.dp)
      )
    }
  }
}


enum class SearchBarMode {
  Default, Fold
}