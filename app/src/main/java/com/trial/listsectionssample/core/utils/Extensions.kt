package com.trial.listsectionssample.core.utils

inline fun <reified T : Any> T.ordinal(): Int {
    if (T::class.isSealed) {
        return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }
    }

    val klass = if (T::class.isCompanion) {
        javaClass.declaringClass
    } else {
        javaClass
    }

    return klass!!.superclass?.classes?.indexOfFirst { it == klass } ?: -1
}
