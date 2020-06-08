package com.trial.listsectionssample.core.modules.offer.usecase

import com.trial.listsectionssample.core.modules.offer.Mapper.mapToOfferDetailDomain
import com.trial.listsectionssample.core.modules.offer.data.OffersRepositoryInterface
import com.trial.listsectionssample.core.modules.offer.domain.Offer
import io.reactivex.Single
import javax.inject.Inject

class GetOfferDetailUseCase @Inject constructor(private val offersRepository: OffersRepositoryInterface) {
    operator fun invoke(id: Int): Single<Offer> {
        return offersRepository.getOfferDetail(id).flatMap {
            Single.just(mapToOfferDetailDomain(it))
        }
    }
}