package com.akhundovmurad.tayqatechtask.data.network.retroift

import com.akhundovmurad.tayqatechtask.data.network.ApiService

class ApiUtils {
    companion object{
        private const val BASE_URL = "http://89.147.202.166:1153/"

        fun getApiService() : ApiService{
            return RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
        }
    }
}