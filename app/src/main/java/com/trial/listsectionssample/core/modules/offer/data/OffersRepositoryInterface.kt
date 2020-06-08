package com.trial.listsectionssample.core.modules.offer.data

import com.trial.listsectionssample.core.modules.offer.entities.Home
import com.trial.listsectionssample.core.modules.offer.entities.OfferDetailsResponse
import io.reactivex.Single

interface OffersRepositoryInterface {
    fun getAllOffers(): Single<Home>

    fun getOfferDetail(id: Int): Single<OfferDetailsResponse>
}