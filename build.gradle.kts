@file:Suppress("SpellCheckingInspection")

plugins {
  id("com.diffplug.spotless") version "5.7.0"
}

subprojects {
  repositories { maven("https://dl.bintray.com/oh-rin/meowbase") }

  apply(plugin = "com.diffplug.spotless")
  spotless {
    kotlin {
      target("**/*.kt")
      targetExclude("$buildDir/**/*.kt")
      targetExclude("bin/**/*.kt")
      licenseHeaderFile(rootDir.resolve("spotless/copyright.kt"))
      ktlint("0.40.0").userData(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2",
          "disabled_rules" to "no-wildcard-imports",
        )
      )
    }
  }
}