package com.akhundovmurad.tayqatechtask.data.local.dao

import com.akhundovmurad.tayqatechtask.data.local.entity.LocalCountry
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject

class LocalCountryDao @Inject constructor(private val boxStore : BoxStore) {

    private val countryBox : Box<LocalCountry> by lazy { boxStore.boxFor(LocalCountry::class.java) }

    fun insertAll(countries: List<LocalCountry>) {
        countryBox.put(countries)
    }

    fun getAllCountries(): List<LocalCountry> {
        return countryBox.all
    }
}