package com.rin.compose.kitty.ui.popular

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.rin.compose.kitty.data.S
import com.rin.compose.kitty.ui.AppViewModel

/**
 * 热门屏幕的 UI 声明
 * UI declaration for popular screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun AppViewModel.Popular(viewModel: PopularViewModel) {
  searchText = S.searchCatStar

  // 观察列表滑动到其他国家的数据变化
  val videos by viewModel.videos.observeAsState()

  val countries by viewModel.countries.observeAsState()

  Background { VideoPlayer(videos!!, Modifier.fillMaxSize()) }

  List(countries!!, Modifier.fillMaxSize()) { country ->
    viewModel.changeVideos(country)
  }
}