package com.guardian.app.entity

import androidx.room.TypeConverter
import com.guardian.app.Constants


class FieldsConverter {
    @TypeConverter
    fun fromFields(item: ArticleItem.Fields) =
        Constants.THUMBNAIL + '_' + item.thumbnail + '_' + Constants.BODY_TEXT + '_' + item.text

    @TypeConverter
    fun toFields(str: String): ArticleItem.Fields {
        val fields = ArticleItem.Fields()
        val lastIndex = str.indexOf('_' + Constants.BODY_TEXT)
        fields.thumbnail = str.substring(Constants.THUMBNAIL.length + 1, lastIndex)
        fields.text = str.substring(lastIndex+Constants.BODY_TEXT.length + 2)
        return fields
    }
}