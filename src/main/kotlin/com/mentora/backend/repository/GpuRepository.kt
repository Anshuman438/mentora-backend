package com.mentora.backend.repository

import com.mentora.backend.model.BookingStatus
import com.mentora.backend.model.GpuBooking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GpuRepository : JpaRepository<GpuBooking, Long> {
    fun findByStudentUidOrderByRequestedAtDesc(uid: String): List<GpuBooking>
    fun findByStatus(status: BookingStatus): List<GpuBooking>
}
