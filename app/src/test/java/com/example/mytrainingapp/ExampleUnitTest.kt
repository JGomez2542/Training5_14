package com.example.mytrainingapp

import com.example.mytrainingapp.data.entities.Entities
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var animal: Entities.Animal

    @Before
    fun setup() {
        animal = Entities.Animal("Dog", "Dog")
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
