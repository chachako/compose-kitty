@file:Suppress("SpellCheckingInspection")
@file:OptIn(InternalMeowbaseApi::class)

buildscript {
  // parse versions.properties file and collect to Map<String, String>
  val versions = rootDir.resolve("versions.properties").readLines()
    .filter { it.contains("=") && !it.startsWith("#") }
    .map { it.substringBeforeLast("=") to it.substringAfterLast("=") }
    .toMap()

  // find newest dependency from the versions.properties file
  fun dep(group: String, artifact: String, versionKey: String? = null) =
    "$group:$artifact:" + versions[versionKey ?: "version.$group..$artifact"]

  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven("https://dl.bintray.com/oh-rin/meowbase")
  }

  listOf(
    "com.meowbase.plugin:toolkit:0.1.15",
    "com.android.tools.build:gradle:7.0.0-alpha08",
    "de.fayard.refreshVersions:refreshVersions:0.9.7",
    dep("org.jetbrains.kotlin", "kotlin-gradle-plugin", "version.kotlin"),
    dep("com.google.dagger", "hilt-android-gradle-plugin", "version.google.dagger.hilt"),
  ).forEach { dependencies.classpath(it) }
}

setupMeowbaseToolkit {
  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + arrayOf(
      "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
      "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi"
    )
  }
  shareMeowbaseAndroidConfig {
    enableCompose = true
    appFullOptions {
      buildFeatures.apply {
        // Disable unused AGP features
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
      }
      packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
      }
    }
  }
  shareDependencies {
    implementationOf(
      AndroidX.appCompat,
      AndroidX.core.ktx,
      AndroidX.compose.ui,
      AndroidX.compose.material,
      AndroidX.compose.animation,
      AndroidX.compose.foundation,
      AndroidX.compose.runtime,
      AndroidX.compose.runtime.liveData,
      AndroidX.lifecycle.runtimeKtx,
      Meowbase.toolkit.core.android,
      Square.retrofit2.retrofit,
      Square.retrofit2.converter.moshi,
      "androidx.compose.ui:ui-util:_",
      "androidx.compose.ui:ui-tooling:_",
      "androidx.paging:paging-compose:+",
      "androidx.activity:activity-compose:+",
      "androidx.navigation:navigation-compose:+",
      "androidx.lifecycle:lifecycle-viewmodel-compose:+",
      "androidx.constraintlayout:constraintlayout-compose:+",
      "dev.chrisbanes.accompanist:accompanist-coil:_",
      "dev.chrisbanes.accompanist:accompanist-insets:_",
    )
    androidTestImplementationOf(
      AndroidX.test.ext.junitKtx,
      "androidx.compose.ui:ui-test-junit4:_",
    )
    testImplementationOf(Kotlin.test.junit)
  }
}

include(":app")
include(":shared-elements")