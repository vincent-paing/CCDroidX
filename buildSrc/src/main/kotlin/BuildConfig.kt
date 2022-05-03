object BuildConfig {
  const val compileSdk = 31
  const val minSdk = 26
  const val targetSdk = 31

  private const val versionMajor = 0
  private const val versionMinor = 0
  private const val versionPatch = 2
  private const val versionBuild = 0

  const val versionName =
    "$versionMajor.$versionMinor.$versionPatch"
  const val versionCode =
    versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild

}