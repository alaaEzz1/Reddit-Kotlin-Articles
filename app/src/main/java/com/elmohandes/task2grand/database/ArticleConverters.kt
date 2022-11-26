package com.elmohandes.task2grand.database

import androidx.room.TypeConverter
import com.elmohandes.task2grand.model.AllAwardings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class ArticleConverters {

    @TypeConverter
    fun stringToListAllAwards(data: String?): List<AllAwardings?>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<AllAwardings?>?>() {}.type
        return gson.fromJson<List<AllAwardings?>>(data, listType)
    }

    @TypeConverter
    fun listAllAwardingsToString(allAwardings: List<AllAwardings?>?): String? {
        val gson = Gson()
        return gson.toJson(allAwardings)
    }

}