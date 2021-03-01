package com.rin.compose.kitty.ui.popular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.PlayerView
import com.rin.compose.kitty.data.PopularVideo
import com.rin.compose.kitty.util.getAsset


/**
 * 具有自动播放猫咪视频的控件
 * The widget that can automatically play kitty videos.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun VideoPlayer(videos: Array<out PopularVideo>, modifier: Modifier) {
  val context = LocalContext.current

  val exoPlayer = remember {
    val extractorsFactory = DefaultExtractorsFactory()
      .setMp4ExtractorFlags(Mp4Extractor.FLAG_WORKAROUND_IGNORE_EDIT_LISTS)
      .setFragmentedMp4ExtractorFlags(FragmentedMp4Extractor.FLAG_WORKAROUND_IGNORE_EDIT_LISTS)
    SimpleExoPlayer.Builder(context)
      .setMediaSourceFactory(DefaultMediaSourceFactory(context, extractorsFactory))
      .build()
      .apply {
        volume = 0f
        shuffleModeEnabled = true
        repeatMode = REPEAT_MODE_ALL
      }
  }

  AndroidView(::PlayerView, modifier) {
    it.player = exoPlayer
    it.useController = false
    it.resizeMode = RESIZE_MODE_ZOOM
    it.setKeepContentOnPlayerReset(true)
  }

  DisposableEffect(videos[0].assetsPath) {
    exoPlayer.playVideos(videos)

    // 不能在这里释放 Player, 因为当 videos 有任何变化它都会被调用
    onDispose { exoPlayer.stop() }
  }

  DisposableEffect(Unit) {
    // 当 Widget 被移除时，安全的释放 Player
    onDispose { exoPlayer.release() }
  }
}

private fun SimpleExoPlayer.playVideos(videos: Array<out PopularVideo>) {
  val mediaItems = videos.map { MediaItem.fromUri(getAsset(it.assetsPath)) }
  setMediaItems(mediaItems, true)
  prepare()
  play()
}