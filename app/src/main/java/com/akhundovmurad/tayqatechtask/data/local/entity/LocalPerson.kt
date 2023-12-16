package com.akhundovmurad.tayqatechtask.data.local.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class LocalPerson(
    @Id
    var id : Long = 0,
    var name : String? = null,
    var surname : String? = null
){
    lateinit var city : ToOne<LocalCity>
}