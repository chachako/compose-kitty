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