package com.dika.starrail.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun StarRailDao(): StarRailDao
}