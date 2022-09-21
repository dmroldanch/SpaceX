package com.aptivist.spacex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchFailureDetails(
    val altitude: Int? = 0,
    val reason: String? = "",
    val time: Int? = 0
) : Parcelable