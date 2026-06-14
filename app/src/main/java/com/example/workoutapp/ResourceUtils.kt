package com.example.workoutapp

import android.content.Context

fun drawableResIdByName(context: Context, name: String?): Int? {
    if (name.isNullOrBlank()) return null
    val resId = context.resources.getIdentifier(name, "drawable", context.packageName)
    return if (resId != 0) resId else null
}