package com.example.assignment.domain.usecase

import com.example.assignment.data.ListItemsModel
import com.example.assignment.data.VedioRepoImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Rishi Porwal
 */
class GetAllVidiosFromLocalCase(private val vedioRepoImp: VedioRepoImp) {

    suspend fun getVedioListUseCase(): List<ListItemsModel> {
        return withContext(Dispatchers.IO){
            vedioRepoImp.getAllVedioFromLocal()
        }
    }

}