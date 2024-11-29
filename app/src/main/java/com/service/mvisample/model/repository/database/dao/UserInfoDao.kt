package com.service.mvisample.model.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.service.mvisample.model.repository.database.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(entity: UserInfoEntity): Long

    @Update
    fun updateUserInfo(entity: UserInfoEntity): Int

    @Delete
    fun delete(entity: UserInfoEntity): Int

    @Query("SELECT * FROM table_userinfo")
    fun fetchAllUsers(): Flow<List<UserInfoEntity>>
}