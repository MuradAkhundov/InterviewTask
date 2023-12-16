package com.akhundovmurad.tayqatechtask.data.local.entity

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class LocalCountry(
    @Id
    var id: Long = 0,
    var name : String? = null){
    @Backlink(to = "country")
    lateinit var cities : ToMany<LocalCity>
}

