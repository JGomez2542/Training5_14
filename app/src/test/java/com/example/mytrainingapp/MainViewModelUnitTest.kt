package com.example.mytrainingapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.example.mytrainingapp.activity.MainViewModel
import com.example.mytrainingapp.adapters.RecyclerTypesAdapter
import com.example.mytrainingapp.data.repository.Repository
import com.example.mytrainingapp.data.entities.Entities
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelUnitTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var repository: Repository
    @Mock
    lateinit var observer: Observer<RecyclerTypesAdapter>
    lateinit var mainViewModel: MainViewModel
    lateinit var mockData: List<Entities>

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(repository)
        mockData = listOf(
            Entities.Person("Jason", 32, 98, true),
            Entities.Person("Jerry", 23, 48, true),
            Entities.Person("John", 45, 100, true),
            Entities.Animal("jonny", "Gerbil"),
            Entities.Planet("Pluto")
        )
    }

    @Test
    fun adapterLiveData_isUpdated() {
        `when`(repository.getData()).thenReturn(mockData)
        mainViewModel.reyclerTypesLiveData.observeForever(observer)
        mainViewModel.getData()
        val argumentCaptor: ArgumentCaptor<RecyclerTypesAdapter> = ArgumentCaptor.forClass(RecyclerTypesAdapter::class.java)
        verify(observer).onChanged(argumentCaptor.capture())
        assert(argumentCaptor.value is RecyclerTypesAdapter)
    }

    @After
    fun tearDown() {
        mainViewModel.reyclerTypesLiveData.removeObserver(observer)
    }
}