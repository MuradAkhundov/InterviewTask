package com.akhundovmurad.tayqatechtask.data.repository

import com.akhundovmurad.tayqatechtask.data.local.dao.LocalCityDao
import com.akhundovmurad.tayqatechtask.data.local.dao.LocalCountryDao
import com.akhundovmurad.tayqatechtask.data.local.dao.LocalPersonDao
import com.akhundovmurad.tayqatechtask.data.local.entity.LocalCity
import com.akhundovmurad.tayqatechtask.data.local.entity.LocalCountry
import com.akhundovmurad.tayqatechtask.data.local.entity.LocalPerson
import com.akhundovmurad.tayqatechtask.data.network.ApiService
import com.akhundovmurad.tayqatechtask.data.network.model.Country
import io.objectbox.BoxStore
import io.objectbox.relation.ToMany
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainRepository(
    private val apiService: ApiService,
    private val localCountryDao: LocalCountryDao,
    private val localCityDao: LocalCityDao,
    private val localPersonDao: LocalPersonDao,
    private val boxStore: BoxStore
) {

    suspend fun getData(): List<Country> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getData()
            response.countryList
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveDataLocally(data: List<Country>) {
        withContext(Dispatchers.IO) {
            val localCountries = mutableListOf<LocalCountry>()
            val localCities = mutableListOf<LocalCity>()
            val localPeople = mutableListOf<LocalPerson>()

            data.forEach { country ->
                val localCountry = LocalCountry(name = country.name)
                localCountries.add(localCountry)

                country.cityList.forEach { city ->
                    val localCity = LocalCity(
                        name = city.name
                    )
                    localCity.country.target = localCountry
                    localCities.add(localCity)

                    city.peopleList.forEach { person ->
                        val localPerson = LocalPerson(
                            name = person.name,
                            surname = person.surname
                        )
                        localPerson.city.target = localCity
                        localPeople.add(localPerson)
                    }
                }
            }
            localCountryDao.insertAll(localCountries)
            localCityDao.insertAll(localCities)
            localPersonDao.insertAll(localPeople)
        }
    }

    suspend fun getLocalData(): List<LocalCountry> = withContext(Dispatchers.IO) {
        localCountryDao.getAllCountries()
    }

    suspend fun clearLocalDb() =
        withContext(Dispatchers.IO) {
                boxStore.removeAllObjects()
        }
}