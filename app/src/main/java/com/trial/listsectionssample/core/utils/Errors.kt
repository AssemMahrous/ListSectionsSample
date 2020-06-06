package com.trial.listsectionssample.core.utils

data class Error(val error: String? = null, val errorRes: Int? = null)

class ApplicationException(var error: Error) : RuntimeException()