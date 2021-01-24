package com.batdemir.template.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batdemir.template.R
import com.batdemir.template.data.entities.db.CityModel
import com.batdemir.template.databinding.ItemCitiesAddBinding
import com.batdemir.template.databinding.ItemCitiesAddHeaderBinding

class CityAdapter(
    private val itemListener: ItemListener? = null
) : ListAdapter<CityAdapter.DataItem, RecyclerView.ViewHolder>(Companion) {

    class MyViewHolderHeader(val binding: ItemCitiesAddHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class MyViewHolder(val binding: ItemCitiesAddBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ItemListener {
        fun addOnClick(model: DataItem)
    }

    companion object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean =
            oldItem.id == newItem.id
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.Item -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                val binding =
                    DataBindingUtil.inflate<ItemCitiesAddHeaderBinding>(
                        layoutInflater,
                        R.layout.item_cities_add_header,
                        parent,
                        false
                    )
                return MyViewHolderHeader(binding)
            }
            ITEM_VIEW_TYPE_ITEM -> {
                val binding =
                    DataBindingUtil.inflate<ItemCitiesAddBinding>(
                        layoutInflater,
                        R.layout.item_cities_add,
                        parent,
                        false
                    )
                return MyViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyViewHolderHeader -> {
                val current = getItem(position) as DataItem.Header
                holder.binding.model = current.name
                holder.binding.executePendingBindings()
            }
            is MyViewHolder -> {
                val current = getItem(position) as DataItem.Item
                holder.binding.model = current.item
                holder.binding.buttonAdd.setOnClickListener {
                    itemListener?.addOnClick(current)
                }
                holder.binding.executePendingBindings()
            }
        }
    }

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    sealed class DataItem {
        abstract val id: Long
        abstract val name: String

        data class Item(val item: CityModel) : DataItem() {
            override val id = item.id
            override val name = item.name
        }

        class Header(override val name: String) : DataItem() {
            override val id = Long.MIN_VALUE
        }
    }
}