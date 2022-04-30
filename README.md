# CCDroidX

CCDroidX is to Android what CCMenu is to Mac and what CCTray is to Windows. CCDroidX is free, as in freedom, build monitoring & alert tool.

## Installation

### Play Store

CCDroidX can be installed from [Play Store](https://play.google.com/store/apps/details?id=dev.aungkyawpaing.ccdroidx). It currently supports Android 8+.

## Build from source

1. First remove the release signing key in app level `build.grade.kts`

```groovy
//  signingConfigs {
//    create("release") {
//      storeFile = File(rootDir, ENV["CCDROIDX_RELEASE_KEYSTORE_PATH"]!!)
//      storePassword = ENV["CCDROIDX_RELEASE_KEYSTORE_PASSWORD"]!!
//      keyAlias = ENV["CCDROIDX_RELEASE_KEY_ALIAS"]!!
//      keyPassword = ENV["CCDROIDX_RELEASE_KEY_ALIAS_PASSWORD"]!!
//    }
//  }
```

2. Remove reference to `signingConfig` in `buildTypes`

```groovy
    getByName("release") {
      isMinifyEnabled = true
      isDebuggable = false
//      signingConfig = signingConfigs.getByName("release")
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
```

3. Build by running in your root folder. This should create an apk under `app/builds/outputs/apk`

```sh
./gradlew assembleDebug
```

If you want to make your own release, you can edit application id and setup your own release keystore. Do note that the app is licenesed under GPL 3.0 so any fork of this has to be open sourced as well.

## Supported Servers

CCDroidX supports all pipelines that supports [CCTray](https://cctray.org/v1/). See [CCTray Servers](https://cctray.org/servers/) for a full list. Do note that CCDroidX does not support servers that requires basic auth for now. The feature will be released in future.

## Contributions

Any forms of contributions are welcomed. Even your bug reports helps CCDroidX grows, so you're more than welcomed to do so. See [Contributing Guide](CONTRIBUTING.md) for more information on how you can help improve the app.
