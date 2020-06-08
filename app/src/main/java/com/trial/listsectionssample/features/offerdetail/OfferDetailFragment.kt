package com.trial.listsectionssample.features.offerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.platform.BaseFragment
import com.trial.listsectionssample.core.platform.ViewModelFactory
import com.trial.listsectionssample.core.utils.Utils.loadImage
import kotlinx.android.synthetic.main.fragment_offer_detail.*
import kotlinx.android.synthetic.main.fragment_offers.no_connection
import kotlinx.android.synthetic.main.no_connection_layout.*
import timber.log.Timber
import javax.inject.Inject


class OfferDetailFragment : BaseFragment<OfferDetailViewModel>() {

    @Inject
    lateinit var viewModel: OfferDetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun getBaseViewModel() = viewModel

    override fun getBaseViewModelFactory() = viewModelFactory
    val args by navArgs<OfferDetailFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.offer.observe(this.viewLifecycleOwner, Observer {
            loadImage(it.image, offer_image)
            tv_brand_title.text = it.brand
            tv_fav.text = it.favorite?.toString() ?: "0"
            tv_offer_title.text = it.title
            tv_offer_description.text = it.description
            tv_price_old.text = it.price?.old
            tv_price_new.text = it.price?.new
            tv_expiration.text = it.expiration
            tv_redemption_value.text = it.redemptionsCap
        })
        viewModel.getDetail(args.id)
        btn_try.setOnClickListener {
            hideNoInternetConnection()
            viewModel.getDetail(args.id)
        }
        back_image.setOnClickListener {
            findNavController().navigateUp()
        }
        heart_image.setOnClickListener {
            Timber.log(1, "Heart has been clicked")
        }
        share_image.setOnClickListener {
            Timber.log(1, "Share has been clicked")
        }
    }

    override fun showNoInternetConnection() {
        no_connection.visibility = View.VISIBLE
    }

    private fun hideNoInternetConnection() {
        no_connection.visibility = View.GONE
    }

}