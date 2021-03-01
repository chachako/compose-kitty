package com.rin.compose.kitty.repository

import javax.inject.Inject

/**
 * 提供个人资料屏幕所需要的数据
 * Provides data to profile screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
class ProfileRepository @Inject constructor() {
  
  /**
   * 获取个人头像
   * Get personal avatar.
   *
   * TODO: Get data from DataStore.
   */
  fun getAvatar(): Any = "https://c-ssl.duitang.com/uploads/blog/202101/02/20210102223458_b869a.thumb.700_0.jpg"
}