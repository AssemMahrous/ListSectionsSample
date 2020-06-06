package com.trial.listsectionssample.core.platform


import com.trial.listsectionssample.core.data.remote.RemoteDataSource
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    val remoteDataSource: RemoteDataSource
) : BaseRepositoryInterface {

    override fun getRemoteService(): RemoteDataSource {
        return remoteDataSource
    }
}