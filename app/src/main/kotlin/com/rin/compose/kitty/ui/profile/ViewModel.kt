package com.rin.compose.kitty.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rin.compose.kitty.repository.PopularRepository
import com.rin.compose.kitty.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 用于管理个人屏幕的数据
 * ViewModel for popular screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class ViewModel @Inject constructor(
  private val repository: ProfileRepository
) : ViewModel() {
  val avatar = MutableLiveData(repository.getAvatar())

}