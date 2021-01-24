package com.batdemir.template.ui.view.cities.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batdemir.template.data.entities.Resource
import com.batdemir.template.data.entities.db.CityModel
import com.batdemir.template.data.repository.CitiesRepository
import com.batdemir.template.ui.adapter.CityAdapter
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CitiesAddViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    companion object {
        private const val CITIES_URL = "https://weathercase-99549.firebaseio.com/.json"
    }

    private val _dataRemote = MutableLiveData<List<CityModel>>()
    private val _dataLocal = MutableLiveData<List<CityModel>>()
    private val _data = MutableLiveData<List<CityAdapter.DataItem>>()

    val data = _data

    fun setData() {
        val newList = mutableListOf<CityAdapter.DataItem>()
        val remoteList = _dataRemote.value
        val localList = _dataLocal.value

        val _remoteList = if (localList == null)
            remoteList
        else
            remoteList?.filter { x -> !localList.contains(x) }

        _remoteList?.groupBy { x -> x.name[0] }?.forEach { (t, u) ->
            newList.add(CityAdapter.DataItem.Header(t.toString()))
            u.forEach { value ->
                newList.add(CityAdapter.DataItem.Item(value))
            }
        }
        _data.value = newList
    }

    fun getDataRemote(): LiveData<Resource<List<CityModel>>> {
        return citiesRepository.getRemote(CITIES_URL)
    }

    fun getDataLocal(): LiveData<Resource<List<CityModel>>> {
        return citiesRepository.getLocal()
    }

    fun setDataRemote(value: List<CityModel>) {
        _dataRemote.value = value
    }

    fun setDataLocal(value: List<CityModel>) {
        _dataLocal.value = value
    }

    fun getFilteredData(query: String): LiveData<List<CityAdapter.DataItem>> {
        val filteredData = MutableLiveData<List<CityAdapter.DataItem>>()
        if (query.isEmpty())
            return _data
        filteredData.value =
            _data.value?.filter { x ->
                x.name.toLowerCase(Locale.ROOT).startsWith(
                    query.toLowerCase(
                        Locale.ROOT
                    )
                )
            }
        return filteredData
    }

    fun addCity(value: CityModel) {
        viewModelScope.launch {
            citiesRepository.addLocal(value)
        }
    }
}