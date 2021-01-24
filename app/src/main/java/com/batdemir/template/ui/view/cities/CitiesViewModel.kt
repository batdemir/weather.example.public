package com.batdemir.template.ui.view.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batdemir.template.data.entities.Resource
import com.batdemir.template.data.entities.db.CityModel
import com.batdemir.template.data.entities.db.ResponseWeatherModel
import com.batdemir.template.data.entities.ui.CitiesItemModel
import com.batdemir.template.data.entities.ui.Weather
import com.batdemir.template.data.repository.CitiesRepository
import com.batdemir.template.data.repository.WeatherRepository
import com.batdemir.template.utils.kelvinToCelsiusString
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _dataLocal = MutableLiveData<List<CityModel>>()
    private val _dataRemote = MutableLiveData<ArrayList<ResponseWeatherModel>>().apply {
        value = arrayListOf()
    }
    private val _data = MutableLiveData<List<CitiesItemModel>>()

    val dataLocal = _dataLocal
    val dataRemote = _dataRemote
    val data = _data

    fun setData() {
        val newList = mutableListOf<CitiesItemModel>()
        val remoteList = _dataRemote.value
        val localList = _dataLocal.value

        localList?.forEachIndexed { index, city ->
            val weatherModel = remoteList?.find { x -> x.name.contains(city.name) }
            newList.add(
                CitiesItemModel(
                    id = city.id.toString(),
                    no = index,
                    weather = Weather.values()
                        .firstOrNull { x -> x.dayIcon == weatherModel?.weather?.first()?.icon || x.nightIcon == weatherModel?.weather?.first()?.icon },
                    city = city,
                    date = weatherModel?.dt,
                    temp = weatherModel?.main?.temp?.kelvinToCelsiusString()
                )
            )
        }
        _data.value = newList
    }

    fun getDataLocal(): LiveData<Resource<List<CityModel>>> {
        return citiesRepository.getLocal()
    }

    fun getDataRemote(cityId: Long): LiveData<Resource<ResponseWeatherModel>> {
        return weatherRepository.getWeather(cityId)
    }

    fun setDataLocal(value: List<CityModel>) {
        _dataLocal.value = value
    }

    fun addDataRemote(value: ResponseWeatherModel) {
        _dataRemote.value?.add(value)
    }

    fun deleteCity(value: CityModel) {
        viewModelScope.launch {
            citiesRepository.deleteLocal(value)
        }
    }
}    