package com.batdemir.template.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batdemir.template.R
import com.batdemir.template.data.entities.ui.CitiesItemModel
import com.batdemir.template.databinding.ItemCitiesBinding

class CitiesAdapter(
    private var isEditModeOn: Boolean = false,
    private val itemListener: ItemListener? = null
) : ListAdapter<CitiesItemModel, CitiesAdapter.MyViewHolder>(Companion) {

    class MyViewHolder(val binding: ItemCitiesBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ItemListener {
        fun deleteOnClick(model: CitiesItemModel)
    }

    companion object : DiffUtil.ItemCallback<CitiesItemModel>() {
        override fun areItemsTheSame(
            oldItem: CitiesItemModel,
            newItem: CitiesItemModel
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: CitiesItemModel,
            newItem: CitiesItemModel
        ): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemCitiesBinding>(
                layoutInflater,
                R.layout.item_cities,
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        holder.binding.isEditModeOn = isEditModeOn
        holder.binding.model = current
        holder.binding.imageViewDelete.setOnClickListener {
            itemListener?.deleteOnClick(current)
        }
        holder.binding.executePendingBindings()
    }

    fun setEditMode(isEditModeOn: Boolean) {
        this.isEditModeOn = isEditModeOn
        notifyDataSetChanged()
    }
}