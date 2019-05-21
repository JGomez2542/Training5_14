package com.example.mytrainingapp.data.repository

import android.arch.lifecycle.MediatorLiveData
import com.example.mytrainingapp.data.entities.Entities


interface Repository {
    val data: List<Entities>
    fun getDataFromDb(): MediatorLiveData<List<Entities>>
}