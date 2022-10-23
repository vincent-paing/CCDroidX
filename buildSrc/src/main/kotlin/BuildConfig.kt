object BuildConfig {
  const val compileSdk = 32
  const val minSdk = 26
  const val targetSdk = 32

  private const val versionMajor = 1
  private const val versionMinor = 1
  private const val versionPatch = 0
  private const val versionBuild = 0

  const val versionName =
    "$versionMajor.$versionMinor.$versionPatch"
  const val versionCode =
    versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild

}