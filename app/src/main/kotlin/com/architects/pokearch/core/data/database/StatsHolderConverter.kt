package com.architects.pokearch.core.data.database

import androidx.room.TypeConverter
import com.architects.pokearch.core.data.database.entities.StatsHolder
import com.architects.pokearch.core.data.database.entities.TypesHolder
import com.google.gson.Gson

class StatsHolderConverter {
    @TypeConverter
    fun fromType(stats: StatsHolder): String = Gson().toJson(stats)
    @TypeConverter
    fun toType(stats: String): StatsHolder = Gson().fromJson(stats, StatsHolder::class.java)
}
