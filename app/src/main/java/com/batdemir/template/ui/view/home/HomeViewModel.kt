package com.batdemir.template.ui.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batdemir.template.data.entities.Resource
import com.batdemir.template.data.entities.db.CityModel
import com.batdemir.template.data.entities.db.ResponseForecastModel
import com.batdemir.template.data.entities.ui.*
import com.batdemir.template.data.repository.CitiesRepository
import com.batdemir.template.data.repository.WeatherRepository
import com.batdemir.template.utils.*
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _dataLocal = MutableLiveData<List<CityModel>>()
    private val _dataRemote = MutableLiveData<ArrayList<ResponseForecastModel>>().apply {
        value = arrayListOf()
    }
    private val _dataList = MutableLiveData<List<MainItemModel>>()
    private val _data = MutableLiveData<MainItemModel>()
    private val _dataHourList = MutableLiveData<List<MainCurrentDayHoursModel>>().apply {
        value = listOf()
    }
    private val _dataHour = MutableLiveData<MainCurrentDayHoursModel>()

    val dataLocal = _dataLocal
    val dataRemote = _dataRemote
    val dataList = _dataList
    val data = _data
    val dataHourList = _dataHourList
    val dataHour = _dataHour

    fun setDataList() {
        val result = mutableListOf<MainItemModel>()
        val remoteList = _dataRemote.value
        val localList = _dataLocal.value

        localList?.forEachIndexed { index, cityModel ->
            val forecastModel = remoteList?.firstOrNull { x -> x.city.id == cityModel.id }
            result.add(
                MainItemModel(
                    cityModel.id,
                    cityModel.name,
                    index == 0,
                    forecastModel?.list?.mapIndexed { _index, listModel ->
                        MainCurrentDayHoursModel(
                            listModel.dt,
                            Weather.values()
                                .firstOrNull { x ->
                                    x.dayIcon == listModel.weather.firstOrNull()?.icon || x.nightIcon == listModel.weather.firstOrNull()?.icon
                                }!!,
                            listModel.dt_txt.toDateFormat(
                                DateFormat.NORMAL_DATE_FORMAT,
                                DateFormat.SMALL_TIME_FORMAT
                            )!!,
                            MainCurrentDetailModel(
                                listModel.wind.speed.toString(),
                                listModel.visibility.toString(),
                                listModel.main.humidity.toString(),
                                listModel.main.tempKf.toString()
                            ),
                            MainCurrentItemModel(
                                Weather.values()
                                    .firstOrNull { x ->
                                        x.dayIcon == listModel.weather.firstOrNull()?.icon || x.nightIcon == listModel.weather.firstOrNull()?.icon
                                    },
                                listModel.main.temp.kelvinToCelsiusString(),
                                listModel.weather.firstOrNull()?.description,
                                listModel.dt_txt.toDateFormat(
                                    DateFormat.NORMAL_DATE_FORMAT,
                                    DateFormat.SHOW_DATE_FORMAT
                                )!!,
                                listModel.main.tempMax.kelvinToCelsiusString(),
                                listModel.main.tempMin.kelvinToCelsiusString()
                            ),
                            _index == 0
                        )
                    },
                    forecastModel?.list?.groupBy { x ->
                        x.dt_txt.toDate(DateFormat.NORMAL_DATE_FORMAT)
                            ?.toString(DateFormat.DAY_NAME_FORMAT)
                    }
                        ?.map { x ->
                            MainWeeklyModel(
                                Long.MIN_VALUE,
                                x.key,
                                Weather.values()
                                    .firstOrNull { weather ->
                                        weather.dayIcon == x.value.firstOrNull()?.weather?.firstOrNull()?.icon || weather.nightIcon == x.value.firstOrNull()?.weather?.firstOrNull()?.icon
                                    },
                                x.value.firstOrNull()?.main?.tempMax?.kelvinToCelsiusString(),
                                x.value.firstOrNull()?.main?.tempMin?.kelvinToCelsiusString()
                            )
                        }
                )
            )
        }

        _dataList.value = result

        if (!result.isNullOrEmpty()) {
            setData(result.first { x -> x.selected }.name)
        }

    }

    fun setData(city: String) {
        _data.value = _dataList.value?.firstOrNull { x -> x.name.contains(city) }
        setDataHourList()
    }

    fun setDataHourList() {
        _dataHourList.value = _data.value?.mainCurrentDayHoursModels
        if (!_dataHourList.value.isNullOrEmpty())
            setDataHour(_dataHourList.value?.first()?.hour ?: "00.00")
    }

    fun setDataHour(hour: String) {
        _dataHour.value = _dataHourList.value?.firstOrNull { x -> x.hour == hour }
    }

    fun getDataLocal(): LiveData<Resource<List<CityModel>>> {
        return citiesRepository.getLocal()
    }

    fun getDataRemote(cityId: Long): LiveData<Resource<ResponseForecastModel>> {
        return weatherRepository.getForecast(cityId)
    }

    fun setDataLocal(value: List<CityModel>) {
        _dataLocal.value = value
    }

    fun addDataRemote(value: ResponseForecastModel) {
        _dataRemote.value?.add(value)
    }
}