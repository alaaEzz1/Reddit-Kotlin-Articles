package com.elmohandes.task2grand.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ArticleData")
data class DataX(
    var title : String?,
    var name : String?,
    @PrimaryKey
    var url :String,
    var selftext :String?,
    var all_awardings : List<AllAwardings>
)
