package com.mentora.backend.controller

import com.mentora.backend.service.LeaveService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

data class LeaveApplyRequest(
    val studentName: String,
    val rollNo: String,
    val type: String,
    val fromDate: String,
    val toDate: String,
    val reason: String,
    val documentUrl: String = ""
)

@RestController
@RequestMapping("/api/leave")
class LeaveController(private val leaveService: LeaveService) {

    @PostMapping("/apply")
    fun applyLeave(
        @AuthenticationPrincipal uid: String,
        @RequestBody request: LeaveApplyRequest
    ) = ResponseEntity.ok(
        leaveService.applyLeave(
            studentUid  = uid,
            studentName = request.studentName,
            rollNo      = request.rollNo,
            type        = request.type,
            fromDate    = LocalDate.parse(request.fromDate),
            toDate      = LocalDate.parse(request.toDate),
            reason      = request.reason,
            documentUrl = request.documentUrl
        )
    )

    @GetMapping("/my")
    fun myLeaves(@AuthenticationPrincipal uid: String) =
        ResponseEntity.ok(leaveService.getStudentLeaves(uid))

    @GetMapping("/pending")
    fun pendingLeaves() =
        ResponseEntity.ok(leaveService.getPendingLeaves())

    @PutMapping("/{id}/approve")
    fun approve(
        @PathVariable id: Long,
        @AuthenticationPrincipal uid: String
    ) = ResponseEntity.ok(leaveService.approveLeave(id, uid))

    @PutMapping("/{id}/reject")
    fun reject(
        @PathVariable id: Long,
        @AuthenticationPrincipal uid: String,
        @RequestBody body: Map<String, String>
    ) = ResponseEntity.ok(leaveService.rejectLeave(id, uid, body["reason"] ?: ""))
}
