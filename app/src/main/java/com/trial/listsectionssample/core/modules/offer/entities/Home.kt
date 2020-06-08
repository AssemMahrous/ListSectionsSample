package com.trial.listsectionssample.core.modules.offer.entities

import com.trial.listsectionssample.core.modules.offer.domain.Offer

data class Home(val title: String, val components: List<ViewComponent>)

sealed class ViewComponent {
    data class Section(val title: String) : ViewComponent() {
        companion object
    }

    data class Item(val offer: Offer) : ViewComponent() {
        companion object
    }
}
