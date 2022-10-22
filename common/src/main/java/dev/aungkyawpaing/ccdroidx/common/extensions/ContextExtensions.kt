package dev.aungkyawpaing.ccdroidx.common.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

fun Context.findActivity(): AppCompatActivity? = when (this) {
  is AppCompatActivity -> this
  is ContextWrapper -> baseContext.findActivity()
  else -> null
}