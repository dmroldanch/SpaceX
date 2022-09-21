package com.aptivist.spacex.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.spacex.domain.models.SpaceXListItem
import com.aptivist.spacex.domain.repository.RepositoryLauches
import com.aptivist.spacex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LauchersViewModel @Inject constructor(private var dataSource: RepositoryLauches) : ViewModel() {

    var searchText = ""
    private var _lauchesList = mutableListOf<SpaceXListItem>()
    private val _error = MutableLiveData<String>()
    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading
    val error get() = _error

    private val _lauchesListFiltered = MutableLiveData<List<SpaceXListItem>>()
    val lauchesListFiltered: LiveData<List<SpaceXListItem>>
        get() = _lauchesListFiltered


    fun updateSearchText(text: CharSequence?) {
        searchText = text.toString()
        if (_lauchesList.isNotEmpty() && searchText.isNotEmpty()) {
            _lauchesListFiltered.value = _lauchesList.filter { _lauchesList ->
                 _lauchesList.mission_name.toString().uppercase().contains(searchText.uppercase()) || _lauchesList.flight_number.toString()
                    .contains(searchText)
            }
        }
    }

    fun getLaunchList() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = dataSource.getLauches()
                when (result) {
                    is Resource.Failure -> _error.postValue( result.message ?: "Error")
                    is Resource.Success -> {
                        _lauchesList.clear()
                        result.data.let { _lauchesList.addAll(it) }
                        launch(Dispatchers.Main) {
                            _lauchesListFiltered.value = _lauchesList
                        }
                        _error.value  = ""
                    }
                }
                _loading.postValue(false)


            } catch (e: Exception) {
                Log.d("Exception",e.message.toString())
                _loading.postValue(false)
              //  ListUIEvents.ShowErrorEvent(e.message ?: "Unknown")
            }
        }
    }

}