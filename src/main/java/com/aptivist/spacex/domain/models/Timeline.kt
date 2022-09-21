package com.aptivist.spacex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Timeline(
    val beco: Int?,
) : Parcelable