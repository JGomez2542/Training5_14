package com.example.mytrainingapp.kotlinReview

import com.example.mytrainingapp.data.entities.Entities

fun main() {
    //Immutable Collections
    val languages = setOf("Kotlin", "Java", "C++") //Creates a read-only set
    val votes = listOf(true, false, false, true) //Creates a read-only list
    val countryToCaptial = mapOf("Germany" to "Berlin", "France" to "Paris") //Creates a read-only map
    val words = listOf("filter", "map", "sorted", "groupBy", "associate")

    //Mutable Collections
    val mutableLanguages = mutableSetOf("Kotlin", "Java", "C++") //Creates a mutable set
    val mutableVotes = mutableListOf(true, false, false, true) //Creates a mutable list
    val mutableCountryToCaptial = mutableMapOf("Germany" to "Berlin", "France" to "Paris")  //Creates a mutable map

    //Arrays
    val testData = arrayOf(0, 1, 5, 9, 10)

    //Indexed access operator
    mutableVotes[1] = true
    testData[2] = 4
    mutableCountryToCaptial["Germany"] = "Bonn"
    println("Votes: $mutableVotes \n Test Data: $testData \n Country to Capital: $mutableCountryToCaptial")

    //Filtering Collections
    val numberOfYesVotes = votes.filter { it }.count()
    println("Number of yes votes: $numberOfYesVotes")

    //Searching a Collection
    val searchResult = countryToCaptial.keys.filter { it.toUpperCase().startsWith("F") }
    searchResult.forEach {
        println("Country: $it")
    }

    //Mapping Collections
    val squared = testData.map { it * it } //Created list with each element squared
    println(squared)

    //Projections using map
    val countries = countryToCaptial.map { it.key }
    println(countries)

    //Grouping Collections
    //This essentially turns an iterable into a map
    val sentence = "This is an example sentence with several words in it"
    val lengthToWords = sentence.split(" ").groupBy { it.length }
    println(lengthToWords)

    //Associating Collections
    var id = 0
    val map = words.associate { id++ to it } //Associates incrementing keys to elements
    println(map)

    //Minimum, Maximum, and Sum
    val people = listOf(
        Entities.Person(name = "Jason", age = 32, score = 98, active = true),
        Entities.Person(name = "Jerry", age = 23, score = 48, active = true),
        Entities.Person(name = "John", age = 45, score = 100, active = true)
    )
    val minAge = people.minBy { it.age }
    val minScore = people.maxBy { it.score }
    val sum = people.sumBy { it.score }
    println("Min Age: ${minAge?.age} Min Score: ${minScore?.score} Sum: $sum")

    //Sorting Collections
    val languagesNaturalOrdering = languages.sorted()
    val languagesDescendingOrdering = languages.sortedDescending()
    val testDataSorted = testData.sortedBy { it % 3 }
    val sortedVotes = votes.sortedWith(Comparator { o1, _ -> if (o1 == true) -1 else 1 })
    val countryToCapitalSorted = countryToCaptial.toSortedMap()

    println(languagesNaturalOrdering)
    println(languagesDescendingOrdering)
    println(testDataSorted)
    println(sortedVotes)
    println(countryToCapitalSorted)

    //Folding Collections
    val testSum = testData.fold(0) { acc, element -> acc + element }
    val testProduct = testData.fold(1) { acc, element -> acc * element }
    println("Test Sum: $testSum Test Product: $testProduct")

    //Chaining function calls
    val users = listOf(
        Entities.Person(name = "Jason", age = 32, score = 98, active = true),
        Entities.Person(name = "Jerry", age = 23, score = 48, active = true),
        Entities.Person(name = "John", age = 45, score = 100, active = true)
    )
    val activeUserNames = users.filter { it.active }
        .take(2)
        .map { it.name }
    println(activeUserNames)

    //Eager Collections
    val cities = listOf("Washington", "Houston", "Seattle", "Worcester", "San Francisco", "Warsaw")

    //Not good
    cities.filter { it.startsWith("W") }
        .map { "City: $it" }
        .take(20)
        .joinToString()

    //Better
    cities.filter { it.startsWith("W") }
        .take(20)
        .map { "City: $it" }
        .joinToString()

    //Lazy Sequences vs Eager Collections
    cities.filter { println("filter: $it"); it.startsWith("W") }
        .map { println("map: $it"); "City: $it" }
        .take(2)
    println()
    println()
    //Lazy Sequence
    cities.asSequence()
        .filter { println("filter: $it"); it.startsWith("W") }
        .also {
            println("Inside also")
        }
        .map { println("map: $it"); "City: $it" }
        .also { println("Inside Also again") }
        .take(2)
        .toList()

    val anotherSequence = sequenceOf(1, 2, 3, 4)
    val andAnotherSequence = generateSequence(0) { i -> i+2 }
}

//data class Person(val name: String, val age: Int, val score: Int, val active: Boolean)