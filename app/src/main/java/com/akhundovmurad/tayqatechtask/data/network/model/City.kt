package com.akhundovmurad.tayqatechtask.data.network.model

data class City(
    val cityId : Int,
    val name : String,
    val peopleList : List<Person>
)