package com.batdemir.template.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batdemir.template.R
import com.batdemir.template.data.entities.ui.MainCurrentDayHoursModel
import com.batdemir.template.databinding.ItemMainHoursBinding

class MainHoursAdapter(private val itemListener: ItemListener) :
    ListAdapter<MainCurrentDayHoursModel, MainHoursAdapter.MyViewHolder>(Companion) {

    class MyViewHolder(val binding: ItemMainHoursBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemListener {
        fun onClick(model: MainCurrentDayHoursModel)
    }

    companion object : DiffUtil.ItemCallback<MainCurrentDayHoursModel>() {
        override fun areItemsTheSame(
            oldItem: MainCurrentDayHoursModel,
            newItem: MainCurrentDayHoursModel
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: MainCurrentDayHoursModel,
            newItem: MainCurrentDayHoursModel
        ): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMainHoursBinding>(
                layoutInflater,
                R.layout.item_main_hours,
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