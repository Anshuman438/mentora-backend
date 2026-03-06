package com.mentora.backend.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "leave_requests")
data class LeaveRequest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val studentUid: String = "",
    val studentName: String = "",
    val rollNo: String = "",
    @Enumerated(EnumType.STRING)
    val type: LeaveType = LeaveType.PERSONAL,
    val fromDate: LocalDate = LocalDate.now(),
    val toDate: LocalDate = LocalDate.now(),
    val reason: String = "",
    val documentUrl: String = "",
    @Enumerated(EnumType.STRING)
    var status: LeaveStatus = LeaveStatus.PENDING,
    val appliedAt: LocalDateTime = LocalDateTime.now(),
    var reviewedAt: LocalDateTime? = null,
    var reviewedBy: String = "",
    var rejectionReason: String = ""
)

enum class LeaveType   { MEDICAL, PERSONAL, EVENT, OTHER }
enum class LeaveStatus { PENDING, APPROVED, REJECTED }
