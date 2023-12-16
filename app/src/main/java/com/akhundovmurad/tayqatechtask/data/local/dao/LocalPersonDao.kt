package com.akhundovmurad.tayqatechtask.data.local.dao

import com.akhundovmurad.tayqatechtask.data.local.entity.LocalPerson
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject

class LocalPersonDao @Inject constructor(private val boxStore: BoxStore) {
    private val personBox: Box<LocalPerson> by lazy { boxStore.boxFor(LocalPerson::class.java) }

    fun insertAll(people: List<LocalPerson>) {
        personBox.put(people)
    }
}