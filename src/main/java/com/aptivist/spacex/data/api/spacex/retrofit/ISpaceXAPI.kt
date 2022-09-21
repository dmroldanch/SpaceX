package com.aptivist.spacex.data.api.spacex.retrofit

import com.aptivist.spacex.data.api.spacex.models.SpaceXListItemDTO
import retrofit2.Response
import retrofit2.http.GET

interface ISpaceXAPI {

    @GET("launches")
    suspend fun getLaunches() : Response<ArrayList<SpaceXListItemDTO>>

}