package com.mentora.backend.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "gpu_bookings")
data class GpuBooking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val studentUid: String = "",
    val resourceName: String = "",
    val bookingDate: LocalDate = LocalDate.now(),
    val timeSlot: String = "",
    val purpose: String = "",
    @Enumerated(EnumType.STRING)
    var status: BookingStatus = BookingStatus.PENDING,
    val requestedAt: LocalDateTime = LocalDateTime.now()
)

enum class BookingStatus { PENDING, APPROVED, REJECTED, COMPLETED }
