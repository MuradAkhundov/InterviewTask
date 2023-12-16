package com.akhundovmurad.tayqatechtask.data.local.entity

import com.akhundovmurad.tayqatechtask.data.network.model.Country
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class LocalCity(
    @Id
    var cityId: Long = 0,
    var name: String? = null,

    ) {
    @Backlink(to = "city")
    lateinit var people: ToMany<LocalPerson>
    lateinit var country : ToOne<LocalCountry>
}