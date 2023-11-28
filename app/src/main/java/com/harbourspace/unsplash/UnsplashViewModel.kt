package com.harbourspace.unsplash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harbourspace.unsplash.api.UnsplashApiProvider
import com.harbourspace.unsplash.data.cb.UnsplashResult
import com.harbourspace.unsplash.data.cb.UnsplashResult2
import com.harbourspace.unsplash.data.model.UnsplashItem
import com.harbourspace.unsplash.data.model.UnsplashPhoto

private const val TAG = "UnsplashViewModel"
class UnsplashViewModel : ViewModel(), UnsplashResult {

  private val _items = MutableLiveData<List<UnsplashItem>>()
  val items: LiveData<List<UnsplashItem>> = _items

  private val provider by lazy {
    UnsplashApiProvider()
  }

  fun fetchImages() {
    provider.fetchImages(this)
  }

  override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
    Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")
    _items.value = images
  }

  override fun onDataFetchedFailed() {
    Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
  }
}

class UnsplashViewModel2 : ViewModel(), UnsplashResult2 {

  private val _items = MutableLiveData<UnsplashPhoto>()
  val items: LiveData<UnsplashPhoto> = _items

  private val provider by lazy {
    UnsplashApiProvider()
  }

  fun fetchImagesDetails(photoId: String) {
    provider.fetchImagesDetails(this, photoId)
  }

  override fun onDataFetchedSuccess(images: List<UnsplashPhoto>) {
    Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")
  }

  override fun onDetailsFetchedSuccess(images: UnsplashPhoto) {
    _items.value = images
  }

  override fun onDataFetchedFailed() {
    Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
  }
}