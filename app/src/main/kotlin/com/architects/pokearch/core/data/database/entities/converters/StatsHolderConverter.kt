package com.architects.pokearch.core.data.database.entities.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class StatsHolderConverter {
    @TypeConverter
    fun fromType(stats: StatsHolder): String = Gson().toJson(stats)
    @TypeConverter
    fun toType(stats: String): StatsHolder = Gson().fromJson(stats, StatsHolder::class.java)
}
