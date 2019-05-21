package com.example.mytrainingapp.data.repository

import com.example.mytrainingapp.data.entities.Entities


interface Repository {
    val data: List<Entities>
}