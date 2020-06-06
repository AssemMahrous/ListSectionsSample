package com.trial.listsectionssample.core.modules.offer.usecase

import com.trial.listsectionssample.core.modules.offer.Mapper.mapToOfferListDomain
import com.trial.listsectionssample.core.modules.offer.data.OffersRepositoryInterface
import com.trial.listsectionssample.core.modules.offer.domain.Offer
import io.reactivex.Single
import javax.inject.Inject

class GetOffersList @Inject constructor(val offersRepository: OffersRepositoryInterface) {

    fun getOfferList(): Single<Pair<String, List<Offer>>> {
        return offersRepository.getAllOffers().flatMap { payload ->
            val list = arrayListOf<Offer>()
            for (section in payload.sections) {
                list.add(mapToOfferListDomain(true, null, section.title))
                for (item in section.items)
                    list.add(mapToOfferListDomain(false, item, ""))
            }
            Single.just(Pair(payload.title, list))
        }
    }
}