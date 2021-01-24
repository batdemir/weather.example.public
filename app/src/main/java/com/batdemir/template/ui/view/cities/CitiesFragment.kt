package com.batdemir.template.ui.view.cities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.batdemir.template.R
import com.batdemir.template.data.entities.Resource
import com.batdemir.template.data.entities.ui.CitiesItemModel
import com.batdemir.template.databinding.FragmentCitiesBinding
import com.batdemir.template.ui.adapter.CitiesAdapter
import com.batdemir.template.ui.base.BaseFragment
import com.batdemir.template.ui.view.MainActivity
import javax.inject.Inject

class CitiesFragment :
    BaseFragment<FragmentCitiesBinding>(R.layout.fragment_cities) {
    @Inject
    lateinit var viewModel: CitiesViewModel
    private lateinit var addItem: MenuItem
    private lateinit var editItem: MenuItem
    private val adapter: CitiesAdapter by lazy {
        CitiesAdapter(false, object : CitiesAdapter.ItemListener {
            override fun deleteOnClick(model: CitiesItemModel) {
                viewModel.deleteCity(model.city!!)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        editItem = menu.add("edit")
        editItem.setIcon(R.drawable.ic_edit)
        editItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        editItem.setOnMenuItemClickListener {
            editItem.isChecked = !editItem.isChecked
            adapter.setEditMode(editItem.isChecked)
            true
        }

        addItem = menu.add("add")
        addItem.setIcon(R.drawable.ic_plus)
        addItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        addItem.setOnMenuItemClickListener {
            findNavController().navigate(CitiesFragmentDirections.actionCitiesFragmentToCitiesAddFragment())
            true
        }
    }

    override fun inject() {
        (requireActivity() as MainActivity).citiesComponent?.inject(this)
    }

    override fun setupDefinition(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding!!.adapter = adapter
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
                                viewModel.setData()
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

        viewModel.data.observe(viewLifecycleOwner, {
            populateData(it)
        })
    }

    override fun setupListener() {
        //("Not yet implemented")
    }

    private fun populateData(values: List<CitiesItemModel>) {
        values.let(adapter::submitList)
    }
}