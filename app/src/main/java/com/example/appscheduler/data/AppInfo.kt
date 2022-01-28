package com.example.appscheduler.data

import android.graphics.drawable.Drawable

data class AppInfo(
    var appName: String? = null,
    var packageName: String? = null,
    var versionName: String? = null,
    var versionCode: Int? = null,
    var icon: Drawable? = null
)
