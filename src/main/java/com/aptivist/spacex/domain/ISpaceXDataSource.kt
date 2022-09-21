package com.aptivist.spacex.domain

import com.aptivist.spacex.data.api.spacex.models.SpaceXListItemDTO

interface ISpaceXDataSource {
    suspend fun getSpaceXList() : List<SpaceXListItemDTO>
}