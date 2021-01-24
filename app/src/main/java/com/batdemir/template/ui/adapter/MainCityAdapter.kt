package com.batdemir.template.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batdemir.template.R
import com.batdemir.template.data.entities.ui.MainItemModel
import com.batdemir.template.databinding.ItemMainCityBinding

class MainCityAdapter(private val itemListener: ItemListener) :
    ListAdapter<MainItemModel, MainCityAdapter.MyViewHolder>(Companion) {

    class MyViewHolder(val binding: ItemMainCityBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemListener {
        fun onClick(model: MainItemModel)
    }

    companion object : DiffUtil.ItemCallback<MainItemModel>() {
        override fun areItemsTheSame(
            oldItem: MainItemModel,
            newItem: MainItemModel
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: MainItemModel,
            newItem: MainItemModel
        ): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMainCityBinding>(
                layoutInflater,
                R.layout.item_main_city,
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        if (current.selected)
            lastSelectedPosition = position
        holder.binding.model = current
        holder.binding.root.setOnClickListener {
            current.selected = true
            if (lastSelectedPosition >= 0 && lastSelectedPosition != position) {
                val before = getItem(lastSelectedPosition)
                before.selected = false
            }
            lastSelectedPosition = position
            notifyDataSetChanged()
            itemListener.onClick(current)
        }
        holder.binding.executePendingBindings()
    }

    private var lastSelectedPosition = -1
}