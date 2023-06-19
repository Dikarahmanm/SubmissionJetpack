package com.dika.starrail.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Character")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val description: String,
    val location: String,
    val photoUrl: String,
    val rating: Double,
    val user: Int,
    var isFavorite: Boolean = false,
)
