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
package com.rin.compose.kitty.data

/**
 * 用于代表小猫的信息
 * Indicates the information of the kitten
 *
 * @author 凛 (https://github.com/RinOrz)
 */
data class Cat(
  val name: String,
  val pictures: List<Any>,
  val avatar: Any? = null,
  val breed: Breed = Breed.Unknown,
  val gender: Gender = Gender.Unknown,
  val socialSite: String? = null,
)
