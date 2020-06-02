package com.example.exampleproj

import com.example.practice.data.models.Data
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitImpl {
    @GET("photos")
    fun getImages(
        @Query("new") new: Boolean? = null,
        @Query("popular") popular: Boolean? = null,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Single<Data>

    companion object Factory {
        fun create(): RetrofitImpl {//todo every time create is called it will receive a new instance. Singleton maybe
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://gallery.dev.webant.ru/api/")
                .build()

            return retrofit.create(RetrofitImpl::class.java)
        }
    }

}
