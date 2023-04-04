object BuildConfig {
  const val compileSdk = 33
  const val minSdk = 26
  const val targetSdk = 33

  private const val versionMajor = 1
  private const val versionMinor = 2
  private const val versionPatch = 0
  private const val versionBuild = 0
  const val versionName =
    "$versionMajor.$versionMinor.$versionPatch"
  const val versionCode =
    versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild * 10

  const val wearVersionCode =
    versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild * 10 + 1
}