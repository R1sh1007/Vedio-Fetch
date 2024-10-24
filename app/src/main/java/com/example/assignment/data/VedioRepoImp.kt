package com.example.assignment.data

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.example.assignment.domain.repository.VideoRepo
/**
 * Created by Rishi Porwal
 */
class VedioRepoImp(private val contentResolver: ContentResolver):VideoRepo {

    override suspend fun getAllVedioFromLocal(): List<ListItemsModel> {
        val vedioItems= mutableListOf<ListItemsModel>()
        val projection= arrayOf(MediaStore.Video.Media._ID,MediaStore.Video.Media.TITLE,MediaStore.Video.Media.DURATION)
        val cursor:Cursor?=contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection,null,null,MediaStore.Video.Media.DATE_ADDED+" DESC ")
        cursor?.apply {
            val idIndex=getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val titleIndex = getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
            val durationIndex = getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            while (moveToNext()){
                val id = getLong(idIndex)
                val title = getString(titleIndex)
                val duration=getLong(durationIndex)
                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(id.toString()).build().toString()
                vedioItems.add(ListItemsModel(title,uri,duration))
            }
        }
        return vedioItems
    }
}