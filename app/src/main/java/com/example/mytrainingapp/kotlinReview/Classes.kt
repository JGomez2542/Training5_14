package com.example.mytrainingapp.kotlinReview

import java.util.*

//1.) Classes and properties
//2.) Getters and setters
//3.) Late-Initialized properties (In testing directory)
//4.) Delegated properties
//5.) String interpolation
//6.) Primary and Secondary constructors
//7.) Type checking and casting
//8.) Extension functions

fun main() {
    //val dog = Animal("Dog")
    //dog.breed = "shepherd"
    //println(dog.species)
    //println(dog.breed)
}

/* Adding properties the hard way
class Animal {
    val species: String

    constructor(species: String) {
        this.species = species
    }
}*/

//Using a primary constructor
/*
class Animal(species: String) {
    val species: String = species
}*/

//Declaring property directly
//class Animal(val species: String)

//Overriding getters and setters
/*class Animal(species: String, breed: String) {
    val species: String = species
        get() = field.toUpperCase()
    var breed = breed
        set(value) {
            field = if (value.startsWith("s")) "Breed starts with s" else value
        }
}*/

//Delegated properties
/*class Cat(species: String) : Animal(species) {
    val age: Int by lazy { //Only executes lambda when age is first accessed
        println("Computing age....")
        Random().nextInt(20)
    }
}

//Primary/Secondary constructors
class Snake(species: String): Animal(species) {
    constructor(petName:String, species: String): this(species) {
        println("Snake's name is $petName")
    }
}*/

//Type Checking (Use is and !is)

//Type Casting (Use as and as? with elvis operator)

//Data classes
data class Contact(val name: String, val phone: String, val favorite: Boolean)

