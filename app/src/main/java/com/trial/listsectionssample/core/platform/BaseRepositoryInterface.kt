package com.trial.listsectionssample.core.platform

import com.trial.listsectionssample.core.data.remote.RemoteDataSource

interface BaseRepositoryInterface {
    fun getRemoteService(): RemoteDataSource
}