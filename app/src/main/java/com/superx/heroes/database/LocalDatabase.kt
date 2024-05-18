package com.superx.heroes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superx.heroes.database.HeroDao
import com.superx.heroes.database.model.Hero

@Database(entities = [Hero::class], version = 4, exportSchema = true)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun heroDao(): HeroDao

}