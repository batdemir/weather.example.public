package com.batdemir.template.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batdemir.template.R
import com.batdemir.template.data.entities.ui.MainWeeklyModel
import com.batdemir.template.databinding.ItemMainWeatherWeeklyBinding

class MainWeeklyAdapter(private val itemListener: ItemListener?  = null) :
    ListAdapter<MainWeeklyModel, MainWeeklyAdapter.MyViewHolder>(Companion) {

    class MyViewHolder(val binding: ItemMainWeatherWeeklyBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ItemListener {
        fun onClick(model: MainWeeklyModel)
    }

    companion object : DiffUtil.ItemCallback<MainWeeklyModel>() {
        override fun areItemsTheSame(
            oldItem: MainWeeklyModel,
            newItem: MainWeeklyModel
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: MainWeeklyModel,
            newItem: MainWeeklyModel
        ): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMainWeatherWeeklyBinding>(
                layoutInflater,
                R.layout.item_main_weather_weekly,
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        holder.binding.model = current
        holder.binding.executePendingBindings()
    }
}