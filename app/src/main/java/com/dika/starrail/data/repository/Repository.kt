package com.dika.starrail.data.repository

import com.dika.starrail.data.local.StarRailDao
import com.dika.starrail.data.local.CharacterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val StarRailDao: StarRailDao) {
    fun getAllCharacter() = StarRailDao.getAllCharacter()
    fun getAllFavoriteCharacter() = StarRailDao.getAllFavoriteCharacter()
    fun getCharacter(id: Int) = StarRailDao.getCharacter(id)
    fun searchCharacter(query: String) = StarRailDao.searchCharacter(query)
    suspend fun insertAllCharacter(Character: List<CharacterEntity>) = StarRailDao.insertAllCharacter(Character)
    suspend fun updateFavoriteCharacter(id: Int, isFavorite: Boolean) = StarRailDao.updateFavoriteCharacter(id, isFavorite)
}