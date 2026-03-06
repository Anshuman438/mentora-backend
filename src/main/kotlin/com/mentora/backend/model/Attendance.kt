package com.mentora.backend.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "attendance")
data class Attendance(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val studentUid: String = "",
    val teacherUid: String = "",
    val subject: String = "",
    val date: LocalDate = LocalDate.now(),
    @Enumerated(EnumType.STRING)
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val markedAt: LocalDateTime = LocalDateTime.now()
)

enum class AttendanceStatus { PRESENT, ABSENT, LATE }
