@file:Suppress("SpellCheckingInspection")

plugins { android; `kotlin-android`; `kotlin-kapt` }

apply(plugin = "dagger.hilt.android.plugin")

android { setupAndroidWithShares("com.rin.compose.kitty") }

dependencies {
  importSharedDependencies(this)
  implementationOf(
    project(":shared-elements"),
    Square.retrofit2.retrofit,
    Square.retrofit2.converter.moshi,
    "com.google.android.exoplayer:exoplayer:_",
    "androidx.hilt:hilt-navigation:_",
    "androidx.hilt:hilt-lifecycle-viewmodel:_",
    "com.google.dagger:hilt-android:_",
  )
  kaptOf(
    Square.moshi.kotlinCodegen,
    "androidx.hilt:hilt-compiler:_",
    "com.google.dagger:hilt-compiler:_",
  )
}