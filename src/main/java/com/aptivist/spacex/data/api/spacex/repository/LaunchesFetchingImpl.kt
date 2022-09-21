package com.aptivist.spacex.data.api.spacex.repository

import android.util.Log
import com.aptivist.spacex.data.api.spacex.mappers.toDomain
import com.aptivist.spacex.data.api.spacex.retrofit.ISpaceXAPI
import com.aptivist.spacex.domain.models.SpaceXListItem
import com.aptivist.spacex.domain.repository.RepositoryLauches
import com.aptivist.spacex.utils.Resource
import java.io.IOException
import javax.inject.Inject

class LaunchesFetchingImpl @Inject constructor(private val spaceXAPI: ISpaceXAPI ) : RepositoryLauches {


    override suspend fun getLauches(): Resource<List<SpaceXListItem>> {
        return try {
            val response = spaceXAPI.getLaunches()
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                Resource.Success(body.map{it.toDomain()})
            } else {
                Resource.Failure("An unexpected error occurred.", null)
            }
        } catch (e: IOException) {
            Log.d("Exception",e.message.toString())
            Resource.Failure("Couldn't reach server. Check your internet connection.", e)
        } catch (e: Exception) {
            Log.d("Exception",e.message.toString())
            Resource.Failure("An unexpected error occurred.", e)
        }
    }

}