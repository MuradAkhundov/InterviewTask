package com.akhundovmurad.tayqatechtask.data.network.model

data class Country(
    val countryId : Int,
    val name : String,
    val cityList: List<City>
)