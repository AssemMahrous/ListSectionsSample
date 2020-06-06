package com.trial.listsectionssample.core.modules.offer.domain

import com.trial.listsectionssample.core.modules.offer.entities.Price

data class Offer(
    val image: String? = null,
    val brand: String? = null,
    val title: String? = null,
    val tags: String? = null,
    val favorite: Int? = null,
    val id: Int? = null,
    val isHead: Boolean,
    val price: Price? = null,
    val redemptionsCap: String? = null,
    val expiration: String? = null,
    val description: String? = null
)