package dev.aungkyawpaing.ccdroidx.feature.add

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.ccdroidx.R
import javax.inject.Inject

class AddProjectErrorMessages @Inject constructor(
  @ApplicationContext private val context: Context
) {

  fun invalidUrlMessage(): String {
    return context.getString(R.string.error_invalid_url)
  }

  fun unsupportedServer(): String {
    return context.getString(R.string.error_unsupported_server)
  }

}