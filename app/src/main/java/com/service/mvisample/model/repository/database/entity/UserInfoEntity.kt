package com.service.mvisample.model.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_userinfo")
class UserInfoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "email")
    var email: String? = null
    @ColumnInfo(name = "avatar")
    var avatar: String? = null
}