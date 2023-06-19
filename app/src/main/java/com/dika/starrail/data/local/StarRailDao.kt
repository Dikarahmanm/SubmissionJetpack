package com.dika.starrail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StarRailDao {
    @Query("SELECT * FROM Character")
    fun getAllCharacter(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE isFavorite = 1")
    fun getAllFavoriteCharacter(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM Character WHERE id = :id")
    fun getCharacter(id: Int): Flow<CharacterEntity>

    @Query("SELECT * FROM Character WHERE name LIKE '%' || :query || '%'")
    fun searchCharacter(query: String): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(CharacterList: List<CharacterEntity>)

    @Query("UPDATE Character SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteCharacter(id: Int, isFavorite: Boolean)
}