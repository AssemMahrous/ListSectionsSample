package com.trial.listsectionssample.core.data.remote.requests

import io.reactivex.Single
import retrofit2.http.*

interface OfferRequests {

    @GET("offers")
    fun getOffersList(
    )
//            : Single<LoginResponse>

    @GET("offers/{id}")
    fun getOfferDetail(@Path("id") id: Int)
//            : Single<LoginResponse>


}
