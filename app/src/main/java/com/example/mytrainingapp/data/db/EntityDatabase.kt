package com.example.mytrainingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mytrainingapp.common.DATABASE_VERSION
import com.example.mytrainingapp.data.entities.Entities

@Database(
    entities = [Entities.Animal::class, Entities.Person::class, Entities.Planet::class],
    version = DATABASE_VERSION
)
abstract class EntityDatabase : RoomDatabase() {

    abstract fun getAnimalDao(): AnimalDao
    abstract fun getPersonDao(): PersonDao
    abstract fun getPlanetDao(): PlanetDao
}