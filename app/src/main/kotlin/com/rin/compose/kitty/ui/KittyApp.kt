package com.rin.compose.kitty.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rin.compose.kitty.data.Screen
import com.rin.compose.kitty.ui.popular.Popular
import com.rin.compose.kitty.ui.popular.gallery.Gallery
import com.rin.compose.kitty.util.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import javax.inject.Inject

/**
 * Kitty App 的 UI 唯一入口点
 * The only UI entry point for the Kitty App.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Composable
fun KittyApp() {
  val navController = rememberNavController()
  val appViewModel = viewModel<AppViewModel>().apply { navigator = navController }
  val searchBarState by appViewModel.searchBarState
  val searchText by appViewModel.searchTextState

  Scaffold(
    appViewModel = appViewModel,
    searchBar = {
      Box(contentAlignment = Alignment.CenterEnd) {
        SearchBar(searchBarState, searchText)
      }
    },
    bottomNavigation = { BottomNavigation() }
  ) {
    NavHost(navController, startDestination = Screen.Popular.route) {
      composable(Screen.Popular.route) { Popular(viewModel = it.viewModel()) }
      composable(Screen.Explore.route) { Popular(viewModel = it.viewModel()) }
      composable(Screen.Favorite.route) { Popular(viewModel = it.viewModel()) }
      composable(Screen.Profile.route) { Popular(viewModel = it.viewModel()) }

      composable(Screen.Popular.Gallery.route) {
        Gallery(
          country = it.arguments?.getString("country") ?: error("Navigation must have parameters!"),
          viewModel = it.viewModel()
        )
      }
    }
  }
}

@Composable
fun Scaffold(
  appViewModel: AppViewModel,
  searchBar: @Composable AppViewModel.() -> Unit,
  bottomNavigation: @Composable AppViewModel.() -> Unit,
  content: @Composable AppViewModel.() -> Unit
) = with(appViewModel) {
  // 刷新背景
  val background by backgroundState
  val fadedBackground by fadedBackgroundState

  background?.let { (modifier, content) ->
    Box(modifier.fillMaxSize()) {
      content()
      // 增加一个遮罩层，以使视频变暗
      if (!fadedBackground) Spacer(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.Black.copy(alpha = 0.8f))
      )

      val backgroundColor by animateColorAsState(
        targetValue = if (fadedBackground) MaterialTheme.colors.background else Color.Transparent,
        animationSpec = tween(1000)
      )
      Spacer(
        modifier = Modifier
          .fillMaxSize()
          .background(backgroundColor)
      )
    }
  }

  ProvideWindowInsets {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)
    ) {
      Spacer(Modifier.statusBarsHeight())

      ConstraintLayout {
        val (titleRef, searchBarRef) = createRefs()

        val title by titleState
        val hideTitle by hideTitleState
        title?.let { (modifier, widget) ->
          val animOffset by animateDpAsState(
            targetValue = if (hideTitle) (-100).dp else 0.dp,
            animationSpec = spring(3f)
          )
          val animAlpha by animateFloatAsState(
            targetValue = if (hideTitle) 0f else 1f,
            animationSpec = spring(3f)
          )
          Box(
            modifier = modifier
              .constrainAs(titleRef) {
                top.linkTo(searchBarRef.top)
                bottom.linkTo(searchBarRef.bottom)
              }
              .offset(y = animOffset)
              .alpha(animAlpha)
          ) { widget() }
        }
        Box(
          modifier = Modifier.constrainAs(searchBarRef) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
          }
        ) { searchBar() }
      }

      Box(
        Modifier
          .weight(1f)
          .padding(horizontal = 6.dp, vertical = 16.dp)
      ) { content() }

      bottomNavigation()
      Spacer(Modifier.navigationBarsHeight())
    }
  }
}

/**
 * 用于管理 Kitty App 的 UI 之间共享的数据
 * Manage UI data shared between Kitty App UI.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
  private val _background = MutableLiveData<Pair<Modifier, @Composable BoxScope.() -> Unit>>()
  private val _title = MutableLiveData<Pair<Modifier, @Composable BoxScope.() -> Unit>>()
  private val _hideTitle = MutableLiveData<Boolean>()
  private val _fadedBackground = MutableLiveData<Boolean>()
  private val _searchText = MutableLiveData<String>()
  private val _searchBarMode = MutableLiveData<SearchBarMode>()

  /**
   * App 上下文的导航控制器
   * Navigation controller for app context.
   */
  lateinit var navigator: NavController

  val titleState @Composable get() = _title.observeAsState()
  val hideTitleState @Composable get() = _hideTitle.observeAsState(false)
  val backgroundState @Composable get() = _background.observeAsState()
  val fadedBackgroundState @Composable get() = _fadedBackground.observeAsState(false)

  /**
   * 搜索栏的显示模式
   * Display mode of the [SearchBar].
   */
  var searchBarMode
    get() = _searchBarMode.value
    set(value) = _searchBarMode.setValue(value)

  val searchBarState
    @Composable get() = _searchBarMode.observeAsState(SearchBarMode.Default)

  /**
   * 搜索栏显示的文本
   * The text show to [SearchBar].
   */
  var searchText
    get() = _searchText.value
    set(value) = _searchText.setValue(value)

  val searchTextState
    @Composable get() = _searchText.observeAsState()

  /**
   * 声明 Kitty UI 脚手架的标题
   * Declare the scaffold title.
   */
  @Composable
  fun Title(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    _title.value = modifier to content
  }

  /**
   * 声明 Kitty UI 脚手架的背景
   * Declare the scaffold background.
   */
  @Composable
  fun Background(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    _background.value = modifier to content
  }

  /**
   * 播放一个背景消失的动画
   * Play a background fade out effect.
   */
  fun fadeOutBackground() {
    _fadedBackground.value = true
  }

  /**
   * 播放一个标题隐藏的动画
   * Play a background hide effect.
   */
  fun hideTitle() {
    _hideTitle.value = true
  }
}