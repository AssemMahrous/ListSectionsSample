package com.trial.listsectionssample.core.modules.offer.data

import com.trial.listsectionssample.core.data.remote.RemoteDataSource
import com.trial.listsectionssample.core.modules.offer.Mapper.mapToOfferListDomain
import com.trial.listsectionssample.core.modules.offer.domain.Offer
import com.trial.listsectionssample.core.modules.offer.entities.*
import com.trial.listsectionssample.core.platform.BaseRepository
import io.reactivex.Single
import javax.inject.Inject

class OffersRepository @Inject constructor(
    remoteDataSource: RemoteDataSource
) : BaseRepository(remoteDataSource), OffersRepositoryInterface {

    override fun getAllOffers(): Single<Home> {
        return remoteDataSource
            .offerRequests
            .getOffersList()
            .flatMap {
                Single.just(
                    Home(
                        it.title,
                        it.sections.toViewComponents()
                    )
                )
            }
    }

    override fun getOfferDetail(id: Int): Single<OfferDetailsResponse> {
        return remoteDataSource
            .offerRequests
            .getOfferDetail(id)
    }
}

private fun List<Section>.toViewComponents(): List<ViewComponent> {
    val list = mutableListOf<ViewComponent>()
    forEach {
        list.add(ViewComponent.Section(it.title))
        it.items.forEach { item ->
            list.add(ViewComponent.Item(item.toOffer()))
        }
    }
    return list
}

private fun Item.toOffer(): Offer = mapToOfferListDomain(false, this, "")
