package com.trial.listsectionssample.core.modules.offer.data

import com.trial.listsectionssample.core.modules.offer.entities.OfferDetailPayload
import com.trial.listsectionssample.core.modules.offer.entities.OffersPayload
import io.reactivex.Single

interface OffersRepositoryInterface {

    fun getAllOffers(): Single<OffersPayload>

    fun getOfferDetail(id: Int): Single<OfferDetailPayload>
}