package ru.gb.criminalintent.model

import java.util.*

// UUID.randomUUID() способ генерирования универсально-уникальных идентификаторов
data class Crime(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    val date: Date = Date(),
    var isSolved: Boolean = false,
    var hard: Int = 0
)
