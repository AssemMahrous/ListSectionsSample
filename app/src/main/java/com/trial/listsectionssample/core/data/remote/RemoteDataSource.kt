package com.trial.listsectionssample.core.data.remote

import com.trial.listsectionssample.core.data.remote.requests.OfferRequests


interface RemoteDataSource {
    val offerRequests: OfferRequests
}
