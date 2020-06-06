package com.trial.listsectionssample.core.di

import com.trial.listsectionssample.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class Modules {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}
