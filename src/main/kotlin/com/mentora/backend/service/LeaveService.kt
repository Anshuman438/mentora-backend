package com.mentora.backend.service

import com.mentora.backend.model.LeaveRequest
import com.mentora.backend.model.LeaveStatus
import com.mentora.backend.model.LeaveType
import com.mentora.backend.repository.LeaveRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class LeaveService(private val leaveRepository: LeaveRepository) {

    fun applyLeave(
        studentUid: String, studentName: String, rollNo: String,
        type: String, fromDate: LocalDate, toDate: LocalDate,
        reason: String, documentUrl: String = ""
    ): LeaveRequest = leaveRepository.save(
        LeaveRequest(
            studentUid  = studentUid, studentName = studentName,
            rollNo      = rollNo,
            type        = LeaveType.valueOf(type.uppercase()),
            fromDate    = fromDate, toDate = toDate,
            reason      = reason,  documentUrl = documentUrl
        )
    )

    fun getStudentLeaves(uid: String): List<LeaveRequest> =
        leaveRepository.findByStudentUidOrderByAppliedAtDesc(uid)

    fun getPendingLeaves(): List<LeaveRequest> =
        leaveRepository.findByStatusOrderByAppliedAtDesc(LeaveStatus.PENDING)

    fun approveLeave(id: Long, teacherUid: String): LeaveRequest {
        val leave = leaveRepository.findById(id)
            .orElseThrow { NoSuchElementException("Leave $id not found") }
        return leaveRepository.save(
            leave.copy(
                status     = LeaveStatus.APPROVED,
                reviewedBy = teacherUid,
                reviewedAt = LocalDateTime.now()
            )
        )
    }

    fun rejectLeave(id: Long, teacherUid: String, reason: String = ""): LeaveRequest {
        val leave = leaveRepository.findById(id)
            .orElseThrow { NoSuchElementException("Leave $id not found") }
        return leaveRepository.save(
            leave.copy(
                status          = LeaveStatus.REJECTED,
                reviewedBy      = teacherUid,
                reviewedAt      = LocalDateTime.now(),
                rejectionReason = reason
            )
        )
    }
}
