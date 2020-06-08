package com.trial.listsectionssample.features.offers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.modules.offer.domain.Offer
import com.trial.listsectionssample.core.modules.offer.entities.ViewComponent
import com.trial.listsectionssample.core.utils.Utils
import com.trial.listsectionssample.core.utils.ordinal
import kotlinx.android.synthetic.main.offer_list_header.view.*
import kotlinx.android.synthetic.main.offer_list_home.view.*

class OffersAdapter(private val listener: (id: Int) -> Unit) :
    ListAdapter<ViewComponent, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewComponent.Section.ordinal() -> HomeOffersHeader(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.offer_list_header, parent, false)
            )
            ViewComponent.Item.ordinal() -> HomeOffersViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.offer_list_home, parent, false)
            )
            else -> {
                throw RuntimeException("The type has to be ONE or TWO")
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).ordinal()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item.ordinal()) {
            ViewComponent.Section.ordinal() ->
                (holder as HomeOffersHeader).bind((item as ViewComponent.Section).title)
            ViewComponent.Item.ordinal() ->
                (holder as HomeOffersViewHolder).bind(
                    (item!! as ViewComponent.Item).offer,
                    listener
                )
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
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ViewComponent>() {
            override fun areItemsTheSame(oldItem: ViewComponent, newItem: ViewComponent): Boolean =
                oldItem.ordinal() == newItem.ordinal()

            override fun areContentsTheSame(
                oldItem: ViewComponent,
                newItem: ViewComponent
            ): Boolean =
                oldItem == newItem
        }
    }
}