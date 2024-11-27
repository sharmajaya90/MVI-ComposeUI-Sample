package com.service.mvisample.model.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.service.mvisample.model.repository.database.entity.MediaInfoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MediaInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMediaInfo(entity: MediaInfoEntity): Long
    @Update
    fun updateMediaInfo(entity: MediaInfoEntity): Int
    @Delete
    fun delete(entity: MediaInfoEntity): Int

    @Query("SELECT * FROM table_mediainfo")
    fun fetchAllRegisterMedia(): Flow<List<MediaInfoEntity>>


    @Query("SELECT * FROM table_mediainfo")
    fun fetchAllFavoriteMedia(): Flow<List<MediaInfoEntity>>
    /*
    @Query("DELETE FROM table_songsinfo WHERE id = :id")
    fun deleteSongsInfoWithId(id: Long): Int
*/
    @Query("SELECT EXISTS(SELECT * FROM table_mediainfo WHERE media_id = :media_id)")
    fun provideIsMediaFavoriteById(media_id: String): Boolean?
}