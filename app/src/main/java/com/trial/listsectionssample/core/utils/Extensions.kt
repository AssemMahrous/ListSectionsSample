package com.trial.listsectionssample.core.utils

import com.trial.listsectionssample.BuildConfig
import io.reactivex.Observable

fun isDevelopmentDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG && BuildConfig.FLAVOR.contains("develop", ignoreCase = true)) {
        block.invoke()
    }
}


