package com.rin.compose.kitty.util


val String.isChinese: Boolean
  get() = this.matches(Regex("[\\u4e00-\\u9fa5]+"))