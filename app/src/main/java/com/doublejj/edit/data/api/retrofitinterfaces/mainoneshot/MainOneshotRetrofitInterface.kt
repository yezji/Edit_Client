package com.doublejj.edit.data.api.retrofitinterfaces.mainoneshot

import com.doublejj.edit.data.models.mainoneshot.MainOneshotResponse
import retrofit2.Call
import retrofit2.http.GET

interface MainOneshotRetrofitInterface {
    @GET("/api/main")
    fun getMainSentences() : Call<MainOneshotResponse>
}