package com.example.mytrainingapp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytrainingapp.activity.MainViewModel
import com.example.mytrainingapp.data.repository.Repository
import java.lang.IllegalArgumentException

class MainViewModelFactory(val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) MainViewModel(repository) as T
        else throw IllegalArgumentException("ViewModel not Found")
    }
}