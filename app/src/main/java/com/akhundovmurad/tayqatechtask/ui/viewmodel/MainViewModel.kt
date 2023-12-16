package com.akhundovmurad.tayqatechtask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhundovmurad.tayqatechtask.data.local.entity.LocalCity
import com.akhundovmurad.tayqatechtask.data.local.entity.LocalCountry
import com.akhundovmurad.tayqatechtask.data.network.model.Country
import com.akhundovmurad.tayqatechtask.data.repository.MainRepository
import com.akhundovmurad.tayqatechtask.filter.FilterItem
import com.akhundovmurad.tayqatechtask.filter.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _data = MutableLiveData<List<Country>>()
    val data: LiveData<List<Country>> get() = _data

    private val _adapterData = MutableLiveData<List<String>>()
    val adapterData: LiveData<List<String>> get() = _adapterData

    private val _countryFilter = MutableLiveData<List<FilterItem>>()
    val countryFilter: LiveData<List<FilterItem>> get() = _countryFilter

    private val _cityFilter = MutableLiveData<List<FilterItem>>()
    val cityFilter: LiveData<List<FilterItem>> get() = _cityFilter


    private val selectedCountries = mutableListOf<FilterItem>()
    private val selectedCities = mutableListOf<FilterItem>()

    init {
        fetchData()
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.Main).launch {
            val localResult = mainRepository.getLocalData()
            if (localResult.isNotEmpty()) {
                val personNames = extractPersonNames(localResult)
                _adapterData.postValue(personNames)

                updateCountryFilter(localResult)
                updateCityFilter(localResult)
            } else {
                val result = mainRepository.getData()
                _data.postValue(result)
                mainRepository.saveDataLocally(result)
                val personNames = extractPersonNames(result)
                _adapterData.postValue(personNames)

                val localResult = mainRepository.getLocalData()

                updateCountryFilter(localResult)
                updateCityFilter(localResult)
            }
        }
    }

    fun refreshData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = mainRepository.getData()
                if (result.isNotEmpty()) {
                    mainRepository.clearLocalDb()
                    mainRepository.saveDataLocally(result)
                    val personNames = extractPersonNames(result)
                    _adapterData.postValue(personNames)

                    val localResult = mainRepository.getLocalData()

                    updateCountryFilter(localResult)
                    updateCityFilter(localResult)
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }

    fun saveSelectedCountries(items: List<FilterItem>) {
        selectedCountries.clear()
        selectedCountries.addAll(items)
    }

    fun saveSelectedCities(items: List<FilterItem>) {
        selectedCities.clear()
        selectedCities.addAll(items)
    }


    fun getSelectedCountries(): List<FilterItem> {
       return selectedCountries
    }

    fun getSelectedCities(): List<FilterItem> = selectedCities.toList()

    private fun updateCountryFilter(data: List<LocalCountry>) {
        val countryFilterItems = data.map { country ->
            country.name?.let { FilterItem(it, FilterType.COUNTRY,true) }
        }
        _countryFilter.postValue(countryFilterItems as List<FilterItem>?)
    }

    private fun updateCityFilter(data: List<LocalCountry>) {
        val cityFilterItems = data.flatMap { country ->
            country.cities.map { city ->
                city.name?.let { FilterItem(it, FilterType.CITY,true) }
            }
        }
        _cityFilter.postValue(cityFilterItems as List<FilterItem>?)
    }


    fun applyFilters(selectedItems: List<FilterItem>, filterType : FilterType) {
        CoroutineScope(Dispatchers.Main).launch {
            val localData = mainRepository.getLocalData()

            when (filterType) {
                FilterType.COUNTRY -> {
                    val filteredData: List<LocalCountry>? = localData.filter { country ->
                        selectedItems.any { it.name == country.name }
                    }
                    _adapterData.postValue(extractPersonNames(filteredData ?: emptyList<LocalCountry>()))
                    filteredData?.let { updateCityFilter(it) }
                }
                FilterType.CITY -> {
                    val filteredData: List<LocalCity>? = localData.flatMap { country ->
                        country.cities.filter { city ->
                            selectedItems.any { it.name == city.name }
                        }
                    }
                    _adapterData.postValue(extractPersonNames(filteredData ?: emptyList<LocalCity>()))
                }
            }
        }
    }

    private fun extractPersonNames(data: List<*>): List<String> {
        return data.flatMap { item ->
            when (item) {
                is LocalCountry -> item.cities.flatMap { city ->
                    city.people.map { person ->
                        "${person.name} ${person.surname}"
                    }
                }
                is Country -> item.cityList.flatMap { city ->
                    city.peopleList.map { person ->
                        "${person.name} ${person.surname}"
                    }
                }
                is LocalCity -> item.people.map { person ->
                    "${person.name} ${person.surname}"
                }
                else -> emptyList()
            }
        }
    }
}
