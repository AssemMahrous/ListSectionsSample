package com.trial.listsectionssample.features.offers

import androidx.lifecycle.MutableLiveData
import com.trial.listsectionssample.core.modules.offer.domain.Offer
import com.trial.listsectionssample.core.modules.offer.usecase.GetOffersList
import ibtikar.tania.user.core.platform.BaseViewModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

class OffersViewModel @Inject constructor(
    private val getOffersList: GetOffersList
) : BaseViewModel() {

    val list = MutableLiveData<List<Offer>>()
    val title = MutableLiveData<String>()

    fun getOffers() {
        subscribe(getOffersList.getOfferList(), Consumer {
            list.postValue(it.second)
            title.postValue(it.first)
        })
    }
}