package com.example.assignment.domain.repository

import com.example.assignment.data.ListItemsModel

/**
 * Created by Rishi Porwal
 */
interface VideoRepo {
    suspend fun getAllVedioFromLocal(): List<ListItemsModel>
}