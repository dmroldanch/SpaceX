package com.aptivist.spacex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Core(
    val block: Int?,
    val core_serial: String?,
    val flight: Int?,
    val gridfins: Boolean?,
    val land_success: Boolean?,
    val landing_intent: Boolean?,
    val landing_type: String?,
    val landing_vehicle: String?,
    val legs: Boolean?,
    val reused: Boolean
) : Parcelable