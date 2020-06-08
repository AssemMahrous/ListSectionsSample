package com.trial.listsectionssample.features.offerdetail

import androidx.lifecycle.MutableLiveData
import com.trial.listsectionssample.core.modules.offer.domain.Offer
import com.trial.listsectionssample.core.modules.offer.usecase.GetOfferDetailUseCase
import com.trial.listsectionssample.core.platform.BaseViewModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

class OfferDetailViewModel @Inject constructor(
    private val getOfferDetail: GetOfferDetailUseCase
) : BaseViewModel() {
    val offer = MutableLiveData<Offer>()
    fun getDetail(id: Int) {
        subscribe(getOfferDetail(id), Consumer {
            offer.postValue(it)
        })
    }
}