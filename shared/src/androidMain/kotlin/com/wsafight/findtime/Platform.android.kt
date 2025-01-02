package com.wsafight.findtime

class AndroidPlatform : Platform {
    override val name: String = "Androi2d ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()