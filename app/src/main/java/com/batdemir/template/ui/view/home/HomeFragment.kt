package com.batdemir.template.ui.view.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.batdemir.template.R
import com.batdemir.template.data.entities.Resource
import com.batdemir.template.data.entities.ui.MainCurrentDayHoursModel
import com.batdemir.template.data.entities.ui.MainItemModel
import com.batdemir.template.databinding.FragmentHomeBinding
import com.batdemir.template.ui.adapter.MainCityAdapter
import com.batdemir.template.ui.adapter.MainHoursAdapter
import com.batdemir.template.ui.adapter.MainWeeklyAdapter
import com.batdemir.template.ui.base.BaseFragment
import com.batdemir.template.ui.view.MainActivity
import javax.inject.Inject

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    @Inject
    lateinit var viewModel: HomeViewModel
    private val adapterCities: MainCityAdapter by lazy {
        MainCityAdapter(object : MainCityAdapter.ItemListener {
            override fun onClick(model: MainItemModel) {
                viewModel.setData(model.name)
            }
        })
    }
    private val adapterHours: MainHoursAdapter by lazy {
        MainHoursAdapter(object : MainHoursAdapter.ItemListener {
            override fun onClick(model: MainCurrentDayHoursModel) {
                viewModel.setDataHour(model.hour)
            }
        })
    }

    private val adapterWeekly: MainWeeklyAdapter by lazy {
        MainWeeklyAdapter()
    }

    override fun inject() {
        (requireActivity() as MainActivity).homeComponent?.inject(this)
    }

    override fun setupDefinition(savedInstanceState: Bundle?) {
        binding!!.adapterCities = adapterCities
        binding!!.adapterHours = adapterHours
        binding!!.adapterWeekly = adapterWeekly
    }

    override fun setupData() {
        viewModel.getDataLocal().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding!!.progressBar.visibility = View.GONE
                    if (it.data.isNullOrEmpty())
                        return@observe
                    viewModel.setDataLocal(it.data)
                }
                Resource.Status.ERROR -> {
                    binding!!.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Resource.Status.LOADING -> {
                    binding!!.progressBar.visibility = View.VISIBLE
                }
            }
        })

        viewModel.dataLocal.observe(viewLifecycleOwner, { dataLocals ->
            dataLocals.forEach { dataLocal ->
                viewModel.getDataRemote(dataLocal.id)
                    .observe(viewLifecycleOwner, { dataRemote ->
                        when (dataRemote.status) {
                            Resource.Status.SUCCESS -> {
                                binding!!.progressBar.visibility = View.GONE
                                if (dataRemote.data == null)
                                    return@observe
                                viewModel.addDataRemote(dataRemote.data)
                                viewModel.setDataList()
                            }
                            Resource.Status.ERROR -> {
                                binding!!.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
                                    dataRemote.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            Resource.Status.LOADING -> {
                                binding!!.progressBar.visibility = View.VISIBLE
                            }
                        }
                    })
            }
        })

        viewModel.dataList.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            it.let(adapterCities::submitList)
        })

        viewModel.data.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            it.mainWeeklyModels.let(adapterWeekly::submitList)
        })

        viewModel.dataHourList.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            it.let(adapterHours::submitList)
        })

        viewModel.dataHour.observe(viewLifecycleOwner, {
            if (it.mainCurrentDetailModel == null) return@observe
            binding!!.detailItemWind.setSubTitle(it.mainCurrentDetailModel.windy)
            binding!!.detailItemVisibility.setSubTitle(it.mainCurrentDetailModel.visibility)
            binding!!.detailItemMoisture.setSubTitle(it.mainCurrentDetailModel.moisture)
            binding!!.detailItemUv.setSubTitle(it.mainCurrentDetailModel.uv)

            if (it.mainCurrentItemModel == null) return@observe
            binding!!.imageViewWeatherIcon.setImageResource(
                it.mainCurrentItemModel.weather?.icon ?: R.drawable.ic_sunny_large
            )
            binding!!.textViewCurrentTemp.text = it.mainCurrentItemModel.temp
            binding!!.textViewCurrentDescription.text = it.mainCurrentItemModel.description
            binding!!.textViewCurrentDate.text = it.mainCurrentItemModel.date
            binding!!.textViewCurrentTempMax.text = it.mainCurrentItemModel.maxTemp
            binding!!.textViewCurrentTempMin.text = it.mainCurrentItemModel.minTemp
        })
    }

    override fun setupListener() {
        //("Not yet implemented")
    }
}