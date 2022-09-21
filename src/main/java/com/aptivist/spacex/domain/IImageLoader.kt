package com.aptivist.spacex.domain

import android.view.View
import android.widget.ImageView

interface IImageLoader {
    fun loadImage(url : String, view: ImageView)
}