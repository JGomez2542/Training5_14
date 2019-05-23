package com.example.mytrainingapp.data.repository

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.room.Room
import com.example.mytrainingapp.common.DB_NAME
import com.example.mytrainingapp.data.db.EntityDatabase
import com.example.mytrainingapp.data.entities.Entities

class RepositoryImpl(context: Context) : Repository {

    private val databaseLiveData: MediatorLiveData<List<Entities>> = MediatorLiveData()
    private val database: EntityDatabase = Room.databaseBuilder(context, EntityDatabase::class.java, DB_NAME).build()

    override val data: List<Entities> by lazy {
        val personList = arrayOf(
            Entities.Person(name = "Jason", age = 32, score = 98, active = true),
            Entities.Person(name = "Jerry", age = 23, score = 48, active = true),
            Entities.Person(name = "John", age = 45, score = 100, active = true)
        )
        val animalList = arrayOf(Entities.Animal(name = "jonny", species = "Gerbil"))
        val planetList = arrayOf(Entities.Planet(planetName = "Pluto"))

        Thread {
            database.getAnimalDao().insert(*animalList)
            database.getPersonDao().insert(*personList)
            database.getPlanetDao().insert(*planetList)
        }.start()
        personList.toList() + animalList.toList() + planetList.toList()
    }

    override fun getDataFromDb(): MediatorLiveData<List<Entities>> {
        databaseLiveData.addSource(database.getPersonDao().getPeople()) {
            databaseLiveData.value = it
        }
        databaseLiveData.addSource(database.getAnimalDao().getAnimals()) {
            databaseLiveData.value = it
        }
        databaseLiveData.addSource(database.getPlanetDao().getPlanet()) {
            databaseLiveData.value = it
        }
        return databaseLiveData
    }
}