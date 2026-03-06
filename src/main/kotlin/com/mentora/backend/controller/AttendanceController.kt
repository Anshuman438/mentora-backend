package com.mentora.backend.controller

import com.mentora.backend.service.AttendanceService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

data class AttendanceEntry(val studentUid: String, val status: String)
data class BulkAttendanceRequest(
    val subject: String,
    val date: String,
    val entries: List<AttendanceEntry>
)

@RestController
@RequestMapping("/api/attendance")
class AttendanceController(private val attendanceService: AttendanceService) {

    @PostMapping("/bulk")
    fun markBulk(
        @AuthenticationPrincipal teacherUid: String,
        @RequestBody request: BulkAttendanceRequest
    ) = ResponseEntity.ok(
        attendanceService.markBulk(
            entries = request.entries.map {
                Triple(it.studentUid, it.status, teacherUid)
            },
            subject = request.subject,
            date    = LocalDate.parse(request.date)
        )
    )

    @GetMapping("/summary")
    fun mySummary(@AuthenticationPrincipal uid: String) =
        ResponseEntity.ok(attendanceService.getStudentSummary(uid))
}
