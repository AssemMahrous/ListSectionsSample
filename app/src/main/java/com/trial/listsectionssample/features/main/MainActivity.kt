package com.trial.listsectionssample.features.main

import android.os.Bundle
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.platform.BaseActivity
import com.trial.listsectionssample.core.platform.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {
    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun getBaseViewModel() = viewModel

    override fun getBaseViewModelFactory() = viewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showNoInternetConnection() {
        //pass to be implemented by the fragments
    }
}