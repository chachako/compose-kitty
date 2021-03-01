package com.rin.compose.kitty.data

/**
 * 储存了来自不同国家的猫咪视频
 * Store popular cat videos from different countries.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
sealed class PopularVideo(val assetsPath: String) {
  class China(fileName: String) : PopularVideo("china/$fileName")
  class America(fileName: String) : PopularVideo("america/$fileName")
  class Korea(fileName: String) : PopularVideo("korea/$fileName")
  class Britain(fileName: String) : PopularVideo("britain/$fileName")
  class Russia(fileName: String) : PopularVideo("russia/$fileName")
  class Sweden(fileName: String) : PopularVideo("sweden/$fileName")

  companion object {
    inline fun <reified T: PopularVideo> named() = T::class.java.simpleName
  }
}