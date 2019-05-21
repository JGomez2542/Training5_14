package com.example.mytrainingapp.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

sealed class Entities {
    @Entity(tableName = "Animals")
    data class Animal(
        val id: Int? = null,
        @PrimaryKey
        val name: String,
        val species: String
    ) : Entities()

    @Entity(tableName = "Planets")
    data class Planet(
        val id: Int? = null,
        @PrimaryKey
        val planetName: String
    ) : Entities()

    @Entity(tableName = "People")
    data class Person(
        val id: Int? = null,
        @PrimaryKey
        val name: String, val age: Int, val score: Int, val active: Boolean
    ) : Entities()
}