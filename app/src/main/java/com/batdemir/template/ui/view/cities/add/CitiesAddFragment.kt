package com.batdemir.template.ui.view.cities.add

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.batdemir.template.R
import com.batdemir.template.data.entities.Resource
import com.batdemir.template.databinding.FragmentCitiesAddBinding
import com.batdemir.template.ui.adapter.CityAdapter
import com.batdemir.template.ui.base.BaseFragment
import com.batdemir.template.ui.view.MainActivity
import com.batdemir.template.utils.hideKeyboard
import javax.inject.Inject

class CitiesAddFragment :
    BaseFragment<FragmentCitiesAddBinding>(R.layout.fragment_cities_add) {
    @Inject
    lateinit var viewModel: CitiesAddViewModel
    private val adapter: CityAdapter by lazy {
        CityAdapter(object : CityAdapter.ItemListener {
            override fun addOnClick(model: CityAdapter.DataItem) {
                val value = model as CityAdapter.DataItem.Item
                viewModel.addCity(value = value.item)
            }
        })
    }

    override fun inject() {
        (requireActivity() as MainActivity).citiesComponent?.inject(this)
    }

    override fun setupDefinition(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding!!.adapter = adapter
    }

    override fun setupData() {
        viewModel.getDataRemote().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding!!.progressBar.visibility = View.GONE
                    if (it.data.isNullOrEmpty())
                        return@observe
                    viewModel.setDataRemote(it.data)
                    viewModel.setData()
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
        viewModel.getDataLocal().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding!!.progressBar.visibility = View.GONE
                    if (it.data.isNullOrEmpty())
                        return@observe
                    viewModel.setDataLocal(it.data)
                    viewModel.setData()
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
        viewModel.data.observe(viewLifecycleOwner, {
            populateData(it)
        })
    }

    override fun setupListener() {
        binding!!.inputLayoutSearch.editText!!.setOnEditorActionListener { _, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH
                || p2.action == KeyEvent.ACTION_DOWN
                && p2.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                populateFilteredData(binding!!.inputLayoutSearch.editText?.text.toString())
            }
            false
        }
        binding!!.inputLayoutSearch.setEndIconOnClickListener {
            populateFilteredData(binding!!.inputLayoutSearch.editText?.text.toString())
        }
    }

    private fun populateData(values: List<CityAdapter.DataItem>) {
        binding!!.progressBar.visibility = View.GONE
        values.let(adapter::submitList)
    }

    private fun populateFilteredData(query: String) {
        hideKeyboard()
        viewModel.getFilteredData(query)
            .observe(viewLifecycleOwner, {
                populateData(it)
            })
    }
}