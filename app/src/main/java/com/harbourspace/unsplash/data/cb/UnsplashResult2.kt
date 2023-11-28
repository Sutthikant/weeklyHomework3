package com.harbourspace.unsplash.data.cb

import com.harbourspace.unsplash.data.model.UnsplashPhoto

interface UnsplashResult2 {
    fun onDataFetchedSuccess(images: List<UnsplashPhoto>)

    fun onDetailsFetchedSuccess(images: UnsplashPhoto)

    fun onDataFetchedFailed()
}
