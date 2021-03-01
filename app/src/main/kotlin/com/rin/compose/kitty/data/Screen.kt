package com.rin.compose.kitty.data

import androidx.annotation.DrawableRes
import com.rin.compose.kitty.R

/**
 * 储存了所有 UI 屏幕的数据
 * Store all UI screen data.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
sealed class Screen(val route: String, @DrawableRes val icon: Int = 0) {
  object Popular : Screen("Popular", R.drawable.ic_popular) {
    object Gallery : Screen("Gallery/{country}") {
      fun route(country: String) = "Gallery/$country"
    }
    object Details : Screen("Details/{country}/{name}") {
      fun route(country: String, name: String) = "Details/$country/$name"
    }
  }
  object Explore : Screen("Explore", R.drawable.ic_explore)
  object Favorite : Screen("Favorite", R.drawable.ic_favorite)
  object Profile : Screen("Profile")
}
