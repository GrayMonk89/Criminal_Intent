package ru.gb.criminalintent.viewmodel

import androidx.lifecycle.ViewModel
import ru.gb.criminalintent.model.Crime

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()
//245
    init {
        for (i in 0 until 100 ){
            val crime = Crime()
            crime.title = "Crime # $i"
            crime.isSolved = i % 2 == 0
            crime.hard = i % 2
            crimes += crime
        }
    }
}