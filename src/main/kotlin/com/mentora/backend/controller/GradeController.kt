package com.mentora.backend.controller

import com.mentora.backend.service.GradeService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

data class AddGradeRequest(
    val studentUid: String,
    val subject: String,
    val testName: String,
    val score: Int,
    val maxScore: Int,
    val date: String,
    val remarks: String = ""
)

@RestController
@RequestMapping("/api/grades")
class GradeController(private val gradeService: GradeService) {

    @PostMapping("/add")
    fun addGrade(
        @AuthenticationPrincipal teacherUid: String,
        @RequestBody request: AddGradeRequest
    ) = ResponseEntity.ok(
        gradeService.addGrade(
            studentUid = request.studentUid,
            teacherUid = teacherUid,
            subject    = request.subject,
            testName   = request.testName,
            score      = request.score,
            maxScore   = request.maxScore,
            date       = LocalDate.parse(request.date),
            remarks    = request.remarks
        )
    )

    @GetMapping("/my")
    fun myGrades(@AuthenticationPrincipal uid: String) =
        ResponseEntity.ok(gradeService.getStudentGrades(uid))

    @GetMapping("/my/{subject}")
    fun myGradesBySubject(
        @AuthenticationPrincipal uid: String,
        @PathVariable subject: String
    ) = ResponseEntity.ok(gradeService.getGradesBySubject(uid, subject))
}
