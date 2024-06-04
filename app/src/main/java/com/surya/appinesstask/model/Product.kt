package com.surya.appinesstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.surya.appinesstask.utils.ImagePathConverter
import com.surya.appinesstask.utils.LanguageConverter
import com.surya.appinesstask.utils.SampleReportsConverter

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    @TypeConverters(LanguageConverter::class) val availableLanguages: List<String>,
    @TypeConverters(SampleReportsConverter::class) val sampleReports: SampleReports,
    val pages: Int,
    val pagesintext: String,
    val report_type: String,
    val authentic: String,
    val remedies: String,
    val vedic: String,
    val price: Int,
    val discount: Int,
    val appDiscount: Int,
    val couponDiscount: Int,
    @TypeConverters(ImagePathConverter::class) val imagePath: ImagePath,
    val soldcount: String,
    val avg: Double
)

data class SampleReports(
    val ENG: String,
    val HIN: String,
    // Add other languages here...
)

data class ImagePath(
    val square: String,
    val wide: String
)


