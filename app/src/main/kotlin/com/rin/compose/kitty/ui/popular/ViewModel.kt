package com.rin.compose.kitty.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rin.compose.kitty.data.PopularVideo
import com.rin.compose.kitty.data.PopularVideo.Companion.named
import com.rin.compose.kitty.repository.PopularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 用于管理热门屏幕的数据
 * ViewModel for popular screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class PopularViewModel @Inject constructor(
  private val repository: PopularRepository
) : ViewModel() {
  val countries = MutableLiveData(repository.countries)

  val videos = MutableLiveData(repository.chinaVideos)

  fun changeVideos(country: String) {
    val videos = when(country) {
      named<PopularVideo.China>() -> repository.chinaVideos
      named<PopularVideo.America>() -> repository.americaVideos
      named<PopularVideo.Britain>() -> repository.britainVideos
      named<PopularVideo.Korea>() -> repository.koreaVideos
      named<PopularVideo.Russia>() -> repository.russiaVideos
      named<PopularVideo.Sweden>() -> repository.swedenVideos
      else -> null
    }
    videos?.let { this.videos.value = it }
  }
}