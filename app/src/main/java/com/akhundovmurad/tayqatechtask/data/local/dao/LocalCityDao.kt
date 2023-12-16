package com.akhundovmurad.tayqatechtask.data.local.dao

import com.akhundovmurad.tayqatechtask.data.local.entity.LocalCity
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject

class LocalCityDao @Inject constructor(private val boxStore: BoxStore) {

    private val cityBox: Box<LocalCity> by lazy { boxStore.boxFor(LocalCity::class.java) }

    fun insertAll(cities: List<LocalCity>) {
        cityBox.put(cities)
    }

}