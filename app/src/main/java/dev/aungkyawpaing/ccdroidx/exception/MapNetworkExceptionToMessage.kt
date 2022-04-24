package dev.aungkyawpaing.ccdroidx.exception

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.api.NetworkException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapNetworkExceptionToMessage @Inject constructor(
  @ApplicationContext private val context: Context
) {
  companion object {
    private const val ERROR_CODE_400 = 400
    private const val ERROR_CODE_401 = 401
    private const val ERROR_CODE_422 = 422
    private const val ERROR_CODE_403 = 403
    private const val ERROR_CODE_404 = 404
    private const val ERROR_CODE_500 = 500
  }

  fun getMessage(exception: NetworkException): String {
    when (exception.errorCode) {
      ERROR_CODE_400 ->
        return exception.errorBody?.let { parseMessageFromErrorBody(it) }
          ?: context.getString(
            R.string.error_server_400
          )
      ERROR_CODE_401 ->
        return exception.errorBody?.let { parseMessageFromErrorBody(it) }
          ?: context.getString(
            R.string.error_generic
          )
      ERROR_CODE_422 ->
        return exception.errorBody?.let { parseMessageFromErrorBody(it) }
          ?: context.getString(
            R.string.error_generic
          )
      ERROR_CODE_403 ->
        return exception.errorBody?.let { parseMessageFromErrorBody(it) }
          ?: context.getString(
            R.string.error_generic
          )
      ERROR_CODE_404 ->
        return context.getString(R.string.error_server_404)
      ERROR_CODE_500 ->
        return context.getString(R.string.error_server_500)
      else ->
        return context.getString(R.string.error_generic)
    }
  }

  private fun parseMessageFromErrorBody(errorBody: String): String {
    try {
      val errorBodyJson = JSONObject(errorBody)

      return errorBodyJson.getString("message")
    } catch (exception: Exception) {
      Timber.e(exception)
    }

    return context.getString(R.string.error_generic)
  }

}