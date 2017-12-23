package com.marvelapp.autentia.comics.data.remote

import com.marvelapp.autentia.comics.ComicDataWrapper
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Conexion API Marvel
 * Created by erubio on 4/11/17.
 */
interface APIService {


    @GET("comics")
    fun getComics(@Query("offset") offset: Int,
                  @Query("limit") limit: Int,
                  @Query("ts") ts: String,
                  @Query("apikey") apikey: String,
                  @Query("hash") hash: String
    ): Observable<ComicDataWrapper>

    @GET("comic/{id}")
    fun getComicById(@Query("offset") offset: Int,
                     @Query("limit") limit: Int,
                     @Query("ts") ts: String,
                     @Query("apikey") apikey: String,
                     @Query("hash") hash: String,
                     @Path("id") id: Int
    ): Observable<ComicDataWrapper>

    companion object {

        fun create(): APIService {


            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://gateway.marvel.com/v1/public/")
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }
}
