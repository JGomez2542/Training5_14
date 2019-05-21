package com.example.mytrainingapp.activity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.mytrainingapp.data.repository.Repository
import com.example.mytrainingapp.data.entities.Entities

class MainViewModel(private val repository: Repository): ViewModel() {
    val reyclerTypesLiveData: MutableLiveData<List<Entities>> = MutableLiveData()

    fun getData() {
        reyclerTypesLiveData.value = repository.data
    }

    fun getMessage(item: Entities) = when (item) {
        is Entities.Animal -> "This is an animal"
        is Entities.Planet -> "This is a planet"
        is Entities.Person -> "This is a person"
    }
}