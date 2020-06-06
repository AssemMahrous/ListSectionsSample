package com.trial.listsectionssample.core.modules.offer.entities

data class OffersPayload(
    val sections: List<Section>,
    val title: String
)

data class Section(
    val items: List<Item>,
    val title: String
)

data class Item(
    val brand: String,
    val detailUrl: String,
    val favoriteCount: Int,
    val imageUrl: String,
    val tags: String,
    val title: String
)