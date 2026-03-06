package com.mentora.backend.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "fee_records")
data class FeeRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val studentUid: String = "",
    val label: String = "",
    val amount: Int = 0,
    val dueDate: LocalDate = LocalDate.now(),
    var paidDate: LocalDate? = null,
    @Enumerated(EnumType.STRING)
    var status: FeeStatus = FeeStatus.DUE,
    val academicYear: String = "2025-26",
    val semester: Int = 1
)

enum class FeeStatus { PAID, DUE, OVERDUE }
