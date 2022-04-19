package dev.aungkyawpaing.ccdroidx.utils.extensions

import android.content.res.Resources

/**
 * Created by Vincent on 2/13/20
 */
fun Int.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Int.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)

fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Float.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)