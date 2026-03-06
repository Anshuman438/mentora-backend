package com.mentora.backend.service

import com.mentora.backend.model.Attendance
import com.mentora.backend.model.AttendanceStatus
import com.mentora.backend.repository.AttendanceRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

data class AttendanceSummary(
    val subject: String,
    val total: Int,
    val present: Int,
    val percentage: Float
)

@Service
class AttendanceService(private val attendanceRepository: AttendanceRepository) {

    fun markBulk(
        entries: List<Triple<String, String, String>>,
        subject: String,
        date: LocalDate
    ): List<Attendance> = attendanceRepository.saveAll(
        entries.map { (uid, status, teacherUid) ->
            Attendance(
                studentUid = uid,
                teacherUid = teacherUid,
                subject    = subject,
                date       = date,
                status     = AttendanceStatus.valueOf(status.uppercase())
            )
        }
    )

    fun getStudentSummary(uid: String): List<AttendanceSummary> {
        val raw = attendanceRepository.getAttendanceSummary(uid)
        return raw.map { row ->
            val subject = row[0] as String
            val total   = (row[1] as Long).toInt()
            val present = (row[2] as Long).toInt()
            AttendanceSummary(
                subject    = subject,
                total      = total,
                present    = present,
                percentage = if (total > 0) present.toFloat() / total else 0f
            )
        }
    }
}
