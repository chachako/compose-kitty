/*
 * Copyright 2021 The Android Open Source Project
 * Copyright 2021 RinOrz (凛)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Github home page: https://github.com/RinOrz
 */
package com.rin.compose.kitty.di

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy
import coil.util.CoilUtils
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * 集中注册所有网络模块
 * Centrally register all network modules.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Module @InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides @Singleton
  fun provideOkHttpClient(@ApplicationContext context: Context) = OkHttpClient.Builder()
    .cache(CoilUtils.createDefaultCache(context))
    .build()

  @Provides @Singleton
  fun provideImageLoader(
    @ApplicationContext context: Context,
    okHttpClient: OkHttpClient
  ) = ImageLoader.Builder(context)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .networkCachePolicy(CachePolicy.ENABLED)
    .okHttpClient(okHttpClient)
    .build()

  @Provides @Singleton
  fun provideRetrofit(
    moshi: Moshi,
    okHttpClient: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://api.thecatapi.com/v1/")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
  }
}
