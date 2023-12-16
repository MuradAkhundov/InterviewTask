package com.akhundovmurad.tayqatechtask.data.network.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("countryList")
    val countryList: List<Country>
)
