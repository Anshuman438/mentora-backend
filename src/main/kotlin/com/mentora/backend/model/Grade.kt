package com.mentora.backend.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "grades")
data class Grade(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val studentUid: String = "",
    val teacherUid: String = "",
    val subject: String = "",
    val testName: String = "",
    val score: Int = 0,
    val maxScore: Int = 100,
    val date: LocalDate = LocalDate.now(),
    val remarks: String = ""
)
