package com.aptivist.spacex.domain.repository

import com.aptivist.spacex.domain.models.SpaceXListItem
import com.aptivist.spacex.utils.Resource


interface RepositoryLauches {
    suspend fun getLauches() : Resource<List<SpaceXListItem>>
}