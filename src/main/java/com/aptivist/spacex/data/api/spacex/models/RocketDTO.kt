package com.aptivist.spacex.data.api.spacex.models

data class RocketDTO(
    val fairings: FairingsDTO?,
    val first_stage: FirstStageDTO?,
    val rocket_id: String?,
    val rocket_name: String?,
    val rocket_type: String?,
    val second_stage: SecondStageDTO?
)