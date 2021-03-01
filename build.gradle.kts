
plugins {
  id("com.diffplug.spotless") version "5.7.0"
}

subprojects {
  apply(plugin = "com.diffplug.spotless")
  spotless {
    kotlin {
      target("**/*.kt")
      targetExclude("$buildDir/**/*.kt")
      targetExclude("bin/**/*.kt")
      ktlint("0.40.0").userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
      licenseHeaderFile(rootDir.resolve("spotless/copyright.kt"))
    }
  }
}