package com.rin.compose.kitty.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.rin.compose.kitty.data.S
import com.rin.compose.kitty.data.Screen
import dev.chrisbanes.accompanist.coil.CoilImage
import com.rin.compose.kitty.ui.profile.ViewModel as ProfileViewModel
import androidx.compose.material.BottomNavigation as ComposeBottomNavigation

@Composable
fun AppViewModel.BottomNavigation() {
  ComposeBottomNavigation(
    backgroundColor = MaterialTheme.colors.onBackground,
    elevation = 0.dp,
    modifier = Modifier
      .height(80.dp)
      .clip(MaterialTheme.shapes.large),
  ) {
    @Composable fun Spacer() {
      Spacer(modifier = Modifier.width(18.dp))
    }

    Spacer()
    Item(navigator, Screen.Popular)
    Item(navigator, Screen.Explore)
    Item(navigator, Screen.Favorite)
    AvatarItem(navigator)
    Spacer()
  }
}

@Composable
private fun RowScope.Item(
  navController: NavController,
  screen: Screen,
  content: @Composable BoxScope.(screen: Screen, isSelected: Boolean) -> Unit = { _, isSelected ->
    val contentColor = LocalContentColor.current
    val iconMask by animateColorAsState(
      if (isSelected) {
        contentColor.copy(alpha = 0.07f)
      } else {
        Color.Unspecified
      }
    )

    Icon(
      painter = painterResource(id = screen.icon),
      contentDescription = screen.iconDesc,
      modifier = Modifier
        .background(iconMask, shape = CircleShape)
        .padding(12.dp)
    )
  }
) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
  val selected = currentRoute == screen.route


  val ripple = rememberRipple(
    bounded = false,
    color = LocalContentColor.current.copy(ContentAlpha.medium)
  )

  Box(
    Modifier
      .weight(1f)
      .align(Alignment.CenterVertically)
      .selectable(
        selected = selected,
        indication = ripple,
        interactionSource = remember { MutableInteractionSource() },
        role = Role.Tab,
      ) { switchScreen(navController, screen) },
    contentAlignment = Alignment.Center
  ) { content(screen, selected) }
}

@Composable
private fun RowScope.AvatarItem(navController: NavController) {
  val profile = viewModel<ProfileViewModel>()
  val avatar by profile.avatar.observeAsState()

  @Composable fun PlaceHolder(color: Color) = Spacer(
    Modifier
      .background(color)
      .clip(CircleShape)
  )

  Item(navController, Screen.Profile) { screen, isSelected ->
    val size by animateDpAsState(if (isSelected) 32.dp else 46.dp)

    CoilImage(
      data = avatar!!,
      modifier = Modifier
        .size(size)
        .clip(CircleShape)
        .clickable { switchScreen(navController, screen) },
      loading = { PlaceHolder(MaterialTheme.colors.onSurface) },
      error = { PlaceHolder(MaterialTheme.colors.error) },
      contentScale = ContentScale.Crop,
      contentDescription = screen.iconDesc,
      fadeIn = true,
    )
  }
}


private val Screen.iconDesc
  @Composable get() = S.screenIconDesc.format(this.route)

private fun switchScreen(
  navController: NavController,
  screen: Screen
) = navController.navigate(screen.route) {
  popUpTo = navController.graph.startDestination
  launchSingleTop = true
}