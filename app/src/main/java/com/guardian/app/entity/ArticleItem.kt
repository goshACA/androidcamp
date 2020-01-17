package com.guardian.app.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "clicked_articles")
open class ArticleItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    lateinit var id: String

    @ColumnInfo(name = "fields")
    @TypeConverters(FieldsConverter::class)
    @SerializedName("fields")
    var fields: Fields = Fields()

    @ColumnInfo(name = "sectionName")
    @SerializedName("sectionName")
    var sectionName: String = ""

    @ColumnInfo(name = "title")
    @SerializedName("webTitle")
    var title: String = ""

    fun getImg() = fields.thumbnail

    class Fields {
        @ColumnInfo(name = "thumbnail")
        @SerializedName("thumbnail")
        var thumbnail: String = ""
        @ColumnInfo(name = "text")
        @SerializedName("bodyText")
        var text: String = ""
    }
}