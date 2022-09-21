package com.aptivist.spacex.data.api.spacex.implementations

import android.content.res.Resources
import com.aptivist.spacex.data.api.spacex.models.SpaceXListItemDTO
import com.aptivist.spacex.data.api.spacex.retrofit.ISpaceXAPI
import com.aptivist.spacex.domain.ISpaceXDataSource
import javax.inject.Inject

class SpaceXDataSource @Inject constructor(private val spaceXAPI: ISpaceXAPI) : ISpaceXDataSource{

    override suspend fun getSpaceXList(): List<SpaceXListItemDTO> {
         val response = spaceXAPI.getLaunches()
         var listLauches : List<SpaceXListItemDTO>? = null
            if(response.isSuccessful){
                listLauches = response.body()
            }
            return listLauches ?: throw Resources.NotFoundException("No info found")
    }

}