package com.aptivist.spacex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SecondStage(
    val block: Int?,
   // val payloads: List<Payload>
) : Parcelable