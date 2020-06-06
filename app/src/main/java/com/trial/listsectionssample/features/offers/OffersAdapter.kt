package com.trial.listsectionssample.features.offers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.modules.offer.domain.Offer
import com.trial.listsectionssample.core.utils.Utils
import kotlinx.android.synthetic.main.offer_list_header.view.*
import kotlinx.android.synthetic.main.offer_list_home.view.*

class OffersAdapter(val listener: (id: Int) -> Unit) :
    ListAdapter<Offer, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    private val TYPE_ONE = 1
    private val TYPE_TWO = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ONE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.offer_list_home, parent, false)
            return HomeOffersViewHolder(view)
        } else if (viewType == TYPE_TWO) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.offer_list_header, parent, false)
            return HomeOffersHeader(view)
        } else {
            throw RuntimeException("The type has to be ONE or TWO")
        }
    }

    override fun getItemViewType(position: Int) =
        if (getItem(position).isHead) TYPE_TWO else TYPE_ONE

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.itemViewType) {
            TYPE_ONE -> (holder as HomeOffersViewHolder).bind(item!!, listener)
            TYPE_TWO -> (holder as HomeOffersHeader).bind(item.title)
        }
    }


    class HomeOffersHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            title: String?
        ) = with(itemView) {
            tv_section_title.text = title
        }
    }

    class HomeOffersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Offer,
            listener: (id: Int) -> Unit
        ) = with(itemView) {
            Utils.loadImage(item.image, offer_image)
            tv_brand.text = item.brand
            tv_title.text = item.title
            tv_tags.text = item.tags
            tv_fav_count.text = item.favorite?.toString() ?: "0"
            itemView.setOnClickListener {
                listener(item.id!!)
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Offer>() {
            override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean =
                oldItem.isHead == newItem.isHead

            override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean =
                oldItem == newItem
        }
    }
}