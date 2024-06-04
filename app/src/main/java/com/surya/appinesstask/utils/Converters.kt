package com.surya.appinesstask.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.surya.appinesstask.model.ImagePath
import com.surya.appinesstask.model.SampleReports

class LanguageConverter {
    @TypeConverter
    fun fromLanguageList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toLanguageList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}

class SampleReportsConverter {
    @TypeConverter
    fun fromSampleReports(value: SampleReports): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toSampleReports(value: String): SampleReports {
        val type = object : TypeToken<SampleReports>() {}.type
        return Gson().fromJson(value, type)
    }
}

class ImagePathConverter {
    @TypeConverter
    fun fromImagePath(value: ImagePath): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toImagePath(value: String): ImagePath {
        val type = object : TypeToken<ImagePath>() {}.type
        return Gson().fromJson(value, type)
    }
}
