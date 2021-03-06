/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trial.listsectionssample.core.di

import androidx.lifecycle.ViewModel
import com.trial.listsectionssample.features.main.MainViewModel
import com.trial.listsectionssample.features.offerdetail.OfferDetailViewModel
import com.trial.listsectionssample.features.offers.OffersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OffersViewModel::class)
    abstract fun bindOffersViewModel(offersViewModel: OffersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfferDetailViewModel::class)
    abstract fun bindOfferDetailViewModel(offerDetailViewModel: OfferDetailViewModel): ViewModel
}
