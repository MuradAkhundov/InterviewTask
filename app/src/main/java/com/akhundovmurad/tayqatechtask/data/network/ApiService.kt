package com.akhundovmurad.tayqatechtask.data.network

import com.akhundovmurad.tayqatechtask.data.network.model.ApiResponse
import com.akhundovmurad.tayqatechtask.data.network.model.Country
import retrofit2.http.GET

interface ApiService {

    @GET("tayqa/tiger/api/development/test/TayqaTech/getdata")
    suspend fun getData(): ApiResponse

}

