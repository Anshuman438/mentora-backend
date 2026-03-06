package com.mentora.backend.repository

import com.mentora.backend.model.Attendance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface AttendanceRepository : JpaRepository<Attendance, Long> {
    fun findByStudentUid(uid: String): List<Attendance>
    fun findByTeacherUidAndDate(teacherUid: String, date: LocalDate): List<Attendance>

    @Query("""
        SELECT a.subject,
               COUNT(a) as total,
               SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) as present
        FROM Attendance a
        WHERE a.studentUid = :uid
        GROUP BY a.subject
    """)
    fun getAttendanceSummary(uid: String): List<Array<Any>>
}
