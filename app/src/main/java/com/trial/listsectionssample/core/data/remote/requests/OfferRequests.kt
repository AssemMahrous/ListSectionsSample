package com.trial.listsectionssample.core.data.remote.requests

import com.trial.listsectionssample.core.modules.offer.entities.OfferDetailPayload
import com.trial.listsectionssample.core.modules.offer.entities.OffersPayload
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface OfferRequests {

    @GET("offers")
    fun getOffersList(
    ): Single<OffersPayload>

    @GET("offers/{id}")
    fun getOfferDetail(@Path("id") id: Int): Single<OfferDetailPayload>
}
