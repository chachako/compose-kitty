package com.rin.compose.kitty.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 注册 Moshi 模块
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Module @InstallIn(SingletonComponent::class)
object MoshiModule {
  @Provides @Singleton
  fun provideMoshi(): Moshi = Moshi.Builder().build()
}