package com.aptivist.spacex.domain.framework

import android.content.Context
import android.widget.ImageView
import com.aptivist.spacex.domain.IImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import javax.inject.Inject


class GlideImageLoader @Inject constructor(private val requestManager: RequestManager) : IImageLoader {
    override fun loadImage(url: String, view: ImageView) {
        requestManager.load(url).into(view)
    }
}