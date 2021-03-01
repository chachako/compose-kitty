plugins { `android-library`; `kotlin-android` }

android { setupAndroidWithShares() }

dependencies { importSharedDependencies(this) }
