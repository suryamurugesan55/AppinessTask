package com.surya.appinesstask.helper

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.surya.appinesstask.model.Product
import com.surya.appinesstask.utils.ImagePathConverter
import com.surya.appinesstask.utils.LanguageConverter
import com.surya.appinesstask.utils.SampleReportsConverter

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(LanguageConverter::class, SampleReportsConverter::class, ImagePathConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}