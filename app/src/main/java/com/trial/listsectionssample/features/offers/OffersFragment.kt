package com.trial.listsectionssample.features.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.platform.BaseFragment
import com.trial.listsectionssample.core.platform.ViewModelFactory
import com.trial.listsectionssample.features.offerdetail.OfferDetailFragmentArgs
import kotlinx.android.synthetic.main.fragment_offers.*
import kotlinx.android.synthetic.main.no_connection_layout.*
import javax.inject.Inject

class OffersFragment : BaseFragment<OffersViewModel>() {
    @Inject
    lateinit var viewModel: OffersViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun getBaseViewModel() = viewModel

    override fun getBaseViewModelFactory() = viewModelFactory
    private val offersAdapter = OffersAdapter {
        findNavController().navigate(
            R.id.action_offerFragment_to_detailFragment,
            OfferDetailFragmentArgs(it).toBundle(),
            null,
            null
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        offers_list.adapter = offersAdapter
        btn_try.setOnClickListener {
            hideNoInternetConnection()
            viewModel.getOffers()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.list.observe(this.viewLifecycleOwner, Observer {
            offersAdapter.submitList(it)
        })
        viewModel.title.observe(this.viewLifecycleOwner, Observer {
            tv_offer_title_header.text = it
        })
        viewModel.getOffers()
    }

    override fun showNoInternetConnection() {
        no_connection.visibility = View.VISIBLE
    }

    private fun hideNoInternetConnection() {
        no_connection.visibility = View.GONE
    }
}