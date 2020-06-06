package com.trial.listsectionssample.core.modules.offer

import com.trial.listsectionssample.core.modules.offer.domain.Offer
import com.trial.listsectionssample.core.modules.offer.entities.Item
import com.trial.listsectionssample.core.modules.offer.entities.OfferDetailPayload

object Mapper {
    fun mapToOfferListDomain(type: Boolean, item: Item?): Offer =
        Offer(
            isHead = type,
            image = item?.imageUrl,
            brand = item?.brand,
            favorite = item?.favoriteCount,
            id = item?.detailUrl?.substringAfterLast("\\/"),
            tags = item?.tags,
            title = item?.title
        )

    fun mapToOfferDetailDomain(item: OfferDetailPayload): Offer =
        Offer(
            isHead = false,
            title = item.title,
            tags = item.tags,
            id = item.id.toString(),
            favorite = item.favoriteCount,
            brand = item.brand,
            image = item.imageUrl,
            description = item.description,
            expiration = item.expiration,
            price = item.price,
            redemptionsCap = item.redemptionsCap
        )
}