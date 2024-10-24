package com.example.assignment.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.ListItemsModel
import com.example.assignment.domain.usecase.GetAllVidiosFromLocalCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Rishi Porwal
 */
class VedioViewModel(private val getAllVidiosFromLocalCase: GetAllVidiosFromLocalCase) :
    ViewModel() {

    private val videosList = MutableLiveData<List<ListItemsModel>>()
    val videoListLiveData: LiveData<List<ListItemsModel>> = videosList

    private val isLoading = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = isLoading


    fun fetchAllVedios() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val vedioList = getAllVidiosFromLocalCase.getVedioListUseCase()
            videosList.postValue(vedioList)
            isLoading.postValue(false)
        }
    }
}