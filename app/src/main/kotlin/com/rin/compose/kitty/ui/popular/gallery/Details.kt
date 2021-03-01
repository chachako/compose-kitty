package com.rin.compose.kitty.ui.popular.gallery

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialArcMotionFactory
import com.mxalbert.sharedelements.MaterialContainerTransformSpec
import com.mxalbert.sharedelements.SharedMaterialContainer
import com.rin.compose.kitty.R
import com.rin.compose.kitty.data.Cat
import com.rin.compose.kitty.data.Gender
import com.rin.compose.kitty.data.S
import com.rin.compose.kitty.ui.Pager
import com.rin.compose.kitty.ui.rememberPagerState
import com.rin.compose.kitty.ui.theme.KittyFont
import com.rin.compose.kitty.util.isChinese
import dev.chrisbanes.accompanist.coil.CoilImage

//
///**
// * 可展开到详情页的 Item
// * The Item can be expanded to the details page.
// *
// * @author 凛 (https://github.com/RinOrz)
// */
//@Composable
//fun DetailsItem() {
//
//}
//

/**
 * 扩充至整个屏幕的详情页
 * The cat details page.
 *
 * @author 凛 (https://github.com/RinOrz)
 * TODO: Complete and optimize this shit code here with more elegant method...
 */
@Composable
fun DetailsScreen(data: Details) {
  val draggableState = rememberDraggableState(onDelta = { /*TODO*/ })
  val state = rememberPagerState(pageCount = data.cat.pictures.size)
  val picture = data.cat.pictures[data.page]

  var showMore by remember { mutableStateOf(false) }

  val topPadding by animateDpAsState(targetValue = if (showMore) 0.dp else 16.dp)
  val height by animateDpAsState(targetValue = if (showMore) 200.dp else 280.dp, spring(2.5f))
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = topPadding)
      .draggable(
        draggableState,
        Orientation.Vertical, onDragStopped = {
          if (it < 200) {
            showMore = true
          }
        }
      )
  ) {
    Pager(
      state,
      modifier = Modifier
        .height(height)
        .graphicsLayer(clip = false)
    ) {
      val alpha by animateFloatAsState(targetValue = if (showMore && currentPage != page) 0f else 1f)
      val moveX by animateDpAsState(targetValue = if (showMore && currentPage != page) 100.dp else 0.dp)
      val horizontalPadding by animateDpAsState(targetValue = if (showMore) 0.dp else 36.dp)
      SharedMaterialContainer(
        key = picture,
        screenKey = data,
        modifier = Modifier
          .padding(horizontal = horizontalPadding)
          .alpha(alpha)
          .offset(x = moveX),
        shape = MaterialTheme.shapes.medium,
        color = Color.Transparent,
        transitionSpec = MaterialFadeOutTransitionSpec
      ) {
        CoilImage(
          data = picture,
          contentDescription = S.catAvatarDesc.format(data.cat.name),
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize(),
        )
      }
    }

    val space by animateDpAsState(targetValue = if (showMore) 24.dp else 54.dp)
    Spacer(modifier = Modifier.height(space))
    val horizontalPadding by animateDpAsState(
      targetValue = if (showMore) 0.dp else 36.dp,
      spring(3f)
    )
    Box(modifier = Modifier.padding(horizontal = horizontalPadding)) {
      val alpha by animateFloatAsState(targetValue = if (showMore) 0f else 1f)
      Text(
        text = "I'm",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onBackground,
        letterSpacing = 5.sp,
        modifier = Modifier.alpha(alpha)
      )
      val yOffset by animateDpAsState(targetValue = if (showMore) 0.dp else 100.dp)
      Row {
        Text(
          text = data.cat.name,
          fontFamily = if (data.cat.name.isChinese) KittyFont.HYZhuZiMeiXinTiW else KittyFont.Impact,
          color = MaterialTheme.colors.onBackground,
          style = MaterialTheme.typography.h2,
          letterSpacing = 5.sp,
          modifier = Modifier.offset(y = yOffset)
        )

        Spacer(modifier = Modifier.width(18.dp))
        val scale by animateFloatAsState(targetValue = if (showMore) 1f else 0f, tween(600, 200))
        val alpha by animateFloatAsState(targetValue = if (showMore) 1f else 0f, tween(600, 200))
        if (data.cat.gender != Gender.Unknown) Icon(
          painter = when (data.cat.gender) {
            Gender.Male -> painterResource(id = R.drawable.ic_male)
            else -> painterResource(id = R.drawable.ic_female)
          },
          tint = when (data.cat.gender) {
            Gender.Male -> Color(0xFF2D60F3)
            else -> Color(0xFFB6569A)
          },
          contentDescription = data.cat.gender.name,
          modifier = Modifier
            .offset(y = 30.dp)
            .size(28.dp)
            .scale(scale)
            .alpha(alpha)
        )
      }
    }

    val showDetails by animateDpAsState(targetValue = if (showMore) 0.dp else 100.dp)
    val alpha by animateFloatAsState(targetValue = if (showMore) 1f else 0f)
    Column(
      modifier = Modifier
        .offset(y = showDetails)
        .alpha(alpha)
    ) {
      Text(
        text = "This is your elegant kitty princess.",
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.alpha(0.8f)
      )
    }


    val fabOffset by animateDpAsState(targetValue = if (showMore) (-10).dp else 200.dp, spring(3f))
    val fabAlpha by animateFloatAsState(targetValue = if (showMore) 1f else 0f, spring(3f))
    Box(
      contentAlignment = Alignment.BottomEnd,
      modifier = Modifier
        .fillMaxSize()
        .offset(y = fabOffset)
        .alpha(fabAlpha)
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_love),
        contentDescription = null,
        tint = MaterialTheme.colors.error,
        modifier = Modifier
          .background(MaterialTheme.colors.onBackground, CircleShape)
          .size(66.dp)
          .padding(14.dp)
      )
    }
  }
}

private val MaterialFadeOutTransitionSpec = MaterialContainerTransformSpec(
  pathMotionFactory = MaterialArcMotionFactory,
  durationMillis = 300,
  fadeMode = FadeMode.Out
)

data class Details(
  val cat: Cat,
  val page: Int
)