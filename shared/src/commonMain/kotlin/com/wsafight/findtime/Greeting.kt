package com.wsafight.findtime

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello222, ${platform.name}!"
    }
}