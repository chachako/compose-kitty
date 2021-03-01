package com.rin.compose.kitty

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import com.rin.compose.kitty.ui.KittyApp
import com.rin.compose.kitty.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setDecorFitsSystemWindows(window, false)
    setContent { MyTheme { KittyApp() } }
  }
}