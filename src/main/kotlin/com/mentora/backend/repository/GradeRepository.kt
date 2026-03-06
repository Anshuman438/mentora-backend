package com.mentora.backend.repository

import com.mentora.backend.model.Grade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GradeRepository : JpaRepository<Grade, Long> {
    fun findByStudentUidOrderByDateDesc(uid: String): List<Grade>
    fun findByStudentUidAndSubject(uid: String, subject: String): List<Grade>
}
