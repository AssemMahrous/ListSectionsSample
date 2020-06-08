package com.trial.listsectionssample.core.modules.offer.usecase

import com.trial.listsectionssample.core.modules.offer.data.OffersRepositoryInterface
import com.trial.listsectionssample.core.modules.offer.entities.Home
import io.reactivex.Single
import javax.inject.Inject

class GetOffersListUseCase @Inject constructor(private val offersRepository: OffersRepositoryInterface) {
    operator fun invoke(): Single<Home> {
        return offersRepository.getAllOffers()
    }
}