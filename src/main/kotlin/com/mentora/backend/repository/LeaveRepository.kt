package com.mentora.backend.repository

import com.mentora.backend.model.LeaveRequest
import com.mentora.backend.model.LeaveStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LeaveRepository : JpaRepository<LeaveRequest, Long> {
    fun findByStudentUidOrderByAppliedAtDesc(uid: String): List<LeaveRequest>
    fun findByStatusOrderByAppliedAtDesc(status: LeaveStatus): List<LeaveRequest>
    fun findByStudentUidAndStatus(uid: String, status: LeaveStatus): List<LeaveRequest>
}
