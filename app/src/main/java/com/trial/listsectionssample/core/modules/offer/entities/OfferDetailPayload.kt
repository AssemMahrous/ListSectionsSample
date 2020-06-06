package com.trial.listsectionssample.core.modules.offer.entities

data class OfferDetailPayload(
    val brand: String,
    val favoriteCount: Int,
    val id: Int,
    val imageUrl: String,
    val expiration: String,
    val price: Price,
    val description: String,
    val redemptionsCap: String,
    val tags: String,
    val title: String
)

data class Price(
    val new: String,
    val old: String
)