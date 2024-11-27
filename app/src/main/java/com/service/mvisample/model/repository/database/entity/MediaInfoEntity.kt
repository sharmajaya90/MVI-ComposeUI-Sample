package com.service.mvisample.model.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_mediainfo")
class MediaInfoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ColumnInfo(name = "media_id")
    var mediaId: String? = null
    @ColumnInfo(name = "media_title")
    var title: String? = null
    @ColumnInfo(name = "media_subtitle")
    var subtitle: String? = null
    @ColumnInfo(name = "media_url")
    var songUrl: String? = null
    @ColumnInfo(name = "media_image_url")
    var imageUrl: String? = null
}
