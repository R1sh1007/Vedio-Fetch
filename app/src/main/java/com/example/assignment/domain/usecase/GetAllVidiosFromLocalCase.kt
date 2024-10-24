package com.example.assignment.domain.usecase

import com.example.assignment.data.ListItemsModel
import com.example.assignment.data.VedioRepoImp
/**
 * Created by Rishi Porwal
 */
class GetAllVidiosFromLocalCase(private val vedioRepoImp: VedioRepoImp) {

        suspend fun getVedioListUseCase():List<ListItemsModel>{
            return vedioRepoImp.getAllVedioFromLocal()
        }

}