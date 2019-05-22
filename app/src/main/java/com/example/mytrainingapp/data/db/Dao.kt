package com.example.mytrainingapp.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.mytrainingapp.data.db.base.BaseDao
import com.example.mytrainingapp.data.entities.Entities


@Dao
abstract class AnimalDao : BaseDao<Entities.Animal> {
    @Query("SELECT * FROM Animals")
    abstract fun getAnimals(): LiveData<List<Entities.Animal>>

    @Query("SELECT COUNT(id) FROM Animals")
    abstract fun getCount(): Int
}

@Dao
abstract class PersonDao : BaseDao<Entities.Person> {
    @Query("SELECT * FROM People")
    abstract fun getPeople(): LiveData<List<Entities.Person>>

    @Query("SELECT COUNT(id) FROM People")
    abstract fun getCount(): Int
}

@Dao
abstract class PlanetDao : BaseDao<Entities.Planet> {
    @Query("SELECT * FROM Planets")
    abstract fun getPlanet(): LiveData<List<Entities.Planet>>

    @Query("SELECT COUNT(id) FROM Planets")
    abstract fun getCount(): Int
}