package com.harbourspace.unsplash.api

import com.harbourspace.unsplash.UnsplashViewModel2
import com.harbourspace.unsplash.data.cb.UnsplashResult
import com.harbourspace.unsplash.data.cb.UnsplashResult2
import com.harbourspace.unsplash.data.model.UnsplashItem
import com.harbourspace.unsplash.data.model.UnsplashPhoto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.unsplash.com/"

class UnsplashApiProvider {
  private val retrofit by lazy {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
      .create<UnsplashApi>()
  }

  fun fetchImages(cb: UnsplashResult) {
    retrofit.fetchPhotos().enqueue(object : Callback<List<UnsplashItem>> {
      override fun onResponse(
        call: Call<List<UnsplashItem>>,
        response: Response<List<UnsplashItem>>
      ) {
        if (response.isSuccessful && response.body() != null) {
          cb.onDataFetchedSuccess(response.body()!!)
        } else {
          cb.onDataFetchedFailed()
        }

      }

      override fun onFailure(call: Call<List<UnsplashItem>>, t: Throwable) {
        cb.onDataFetchedFailed()
      }
    })
  }

  fun fetchImagesDetails(cb: UnsplashResult2, photoID: String) {
    retrofit.fetchPhotosDetails(photoID).enqueue(object : Callback<UnsplashPhoto> {
      override fun onResponse(
        call: Call<UnsplashPhoto>,
        response: Response<UnsplashPhoto>
      ) {
        if (response.isSuccessful && response.body() != null) {
          cb.onDetailsFetchedSuccess(response.body()!!)
        } else {
          cb.onDataFetchedFailed()
        }

      }

      override fun onFailure(call: Call<UnsplashPhoto>, t: Throwable) {
        cb.onDataFetchedFailed()
      }
    })
  }
}

