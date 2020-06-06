package com.trial.listsectionssample.core.modules.offer.data

import com.trial.listsectionssample.core.data.remote.RemoteDataSource
import com.trial.listsectionssample.core.modules.offer.entities.OfferDetailPayload
import com.trial.listsectionssample.core.modules.offer.entities.OffersPayload
import com.trial.listsectionssample.core.platform.BaseRepository
import io.reactivex.Single
import javax.inject.Inject

class OffersRepository @Inject constructor(
    remoteDataSource: RemoteDataSource
) : BaseRepository(remoteDataSource), OffersRepositoryInterface {

    override fun getAllOffers(): Single<OffersPayload> {
        return remoteDataSource
            .offerRequests
            .getOffersList()

    }

    override fun getOfferDetail(id: Int): Single<OfferDetailPayload> {
        return remoteDataSource
            .offerRequests
            .getOfferDetail(id)
    }
}