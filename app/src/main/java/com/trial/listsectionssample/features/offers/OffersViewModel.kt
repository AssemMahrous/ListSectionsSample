package com.trial.listsectionssample.features.offers

import androidx.lifecycle.MutableLiveData
import com.trial.listsectionssample.core.modules.offer.entities.ViewComponent
import com.trial.listsectionssample.core.modules.offer.usecase.GetOffersListUseCase
import com.trial.listsectionssample.core.platform.BaseViewModel
import com.trial.listsectionssample.core.utils.SingleLiveEvent
import io.reactivex.functions.Consumer
import javax.inject.Inject

class OffersViewModel @Inject constructor(
    private val getOffersList: GetOffersListUseCase
) : BaseViewModel() {

    val list = SingleLiveEvent<List<ViewComponent>>()
    val title = MutableLiveData<String>()

    fun getOffers() {
        if (title.value.isNullOrEmpty()) subscribe(getOffersList(), Consumer {
            list.postValue(it.components)
            title.postValue(it.title)
        })
    }
}