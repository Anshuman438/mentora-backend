package com.mentora.backend.repository

import com.mentora.backend.model.FeeRecord
import com.mentora.backend.model.FeeStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeeRepository : JpaRepository<FeeRecord, Long> {
    fun findByStudentUid(uid: String): List<FeeRecord>
    fun findByStudentUidAndStatus(uid: String, status: FeeStatus): List<FeeRecord>
}
