package com.superx.heroes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.superx.heroes.database.model.Hero
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroesList(heroes: List<Hero>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHero(hero: Hero)

    @Query("SELECT * FROM Hero WHERE id = :id")
    fun getHeroById(id: Int): Flow<Hero>

    @Query("SELECT * FROM Hero ORDER BY id ASC")
    fun getAllHeroes(): Flow<List<Hero>>
}