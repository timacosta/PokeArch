package com.architects.pokearch.core.data.database

import androidx.room.TypeConverter
import com.architects.pokearch.core.data.database.entities.TypesHolder
import com.google.gson.Gson

class TypesHolderConverter {
    @TypeConverter
    fun fromType(types: TypesHolder): String = Gson().toJson(types)
    @TypeConverter
    fun toType(types: String): TypesHolder = Gson().fromJson(types, TypesHolder::class.java)
}
