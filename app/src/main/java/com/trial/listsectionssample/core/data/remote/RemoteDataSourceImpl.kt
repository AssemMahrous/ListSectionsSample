package com.trial.listsectionssample.core.data.remote


import com.trial.listsectionssample.core.data.remote.requests.OfferRequests
import retrofit2.Retrofit

class RemoteDataSourceImpl(retrofit: Retrofit) :
    RemoteDataSource {
    override val offerRequests: OfferRequests = retrofit.create(OfferRequests::class.java)
}
