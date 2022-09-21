package com.aptivist.spacex.data.api.spacex.models

data class FairingsDTO(
    val recovered: Boolean?,
    val recovery_attempt: Boolean?,
    val reused: Boolean?,
    val ship: String?
)