package com.mentora.backend.service

import com.mentora.backend.model.Grade
import com.mentora.backend.repository.GradeRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GradeService(private val gradeRepository: GradeRepository) {

    fun addGrade(
        studentUid: String, teacherUid: String, subject: String,
        testName: String, score: Int, maxScore: Int,
        date: LocalDate, remarks: String = ""
    ): Grade = gradeRepository.save(
        Grade(
            studentUid = studentUid, teacherUid = teacherUid,
            subject    = subject,   testName   = testName,
            score      = score,     maxScore   = maxScore,
            date       = date,      remarks    = remarks
        )
    )

    fun getStudentGrades(uid: String): List<Grade> =
        gradeRepository.findByStudentUidOrderByDateDesc(uid)

    fun getGradesBySubject(uid: String, subject: String): List<Grade> =
        gradeRepository.findByStudentUidAndSubject(uid, subject)
}
