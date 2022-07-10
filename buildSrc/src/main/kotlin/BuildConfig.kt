object BuildConfig {
  const val compileSdk = 32
  const val minSdk = 26
  const val targetSdk = 32

  private const val versionMajor = 0
  private const val versionMinor = 0
  private const val versionPatch = 5
  private const val versionBuild = 0

  const val versionName =
    "$versionMajor.$versionMinor.$versionPatch"
  const val versionCode =
    versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild

}