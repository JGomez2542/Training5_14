package com.example.mytrainingapp.data.repository

import androidx.lifecycle.MediatorLiveData
import com.example.mytrainingapp.data.entities.Entities


interface Repository {
    val data: List<Entities>
    fun getDataFromDb(): MediatorLiveData<List<Entities>>
}