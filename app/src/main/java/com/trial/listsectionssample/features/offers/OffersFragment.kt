package com.trial.listsectionssample.features.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.platform.ViewModelFactory
import ibtikar.tania.user.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_offers.*
import javax.inject.Inject

class OffersFragment : BaseFragment<OffersViewModel>() {
    @Inject
    lateinit var viewModel: OffersViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun getBaseViewModel() = viewModel

    override fun getBaseViewModelFactory() = viewModelFactory
    private val offersAdapter = OffersAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        offers_list.adapter = offersAdapter
        viewModel.list.observe(this.viewLifecycleOwner, Observer {
            offersAdapter.submitList(it)
        })
        viewModel.title.observe(this.viewLifecycleOwner, Observer {
            tv_offer_title_header.text = it
        })

        viewModel.getOffers()

    }

    override fun showNoInternetConnection() {

    }
}