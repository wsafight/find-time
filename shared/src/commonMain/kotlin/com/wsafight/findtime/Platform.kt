package com.wsafight.findtime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform