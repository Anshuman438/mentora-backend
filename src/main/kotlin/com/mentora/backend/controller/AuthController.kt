package com.mentora.backend.controller

import com.mentora.backend.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

data class RegisterRequest(
    val name: String,
    val email: String,
    val role: String,
    val rollNo: String = "",
    val photoUrl: String = ""
)

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(
        @AuthenticationPrincipal uid: String,
        @RequestBody request: RegisterRequest
    ) = ResponseEntity.ok(
        authService.registerOrUpdateUser(
            uid      = uid,
            name     = request.name,
            email    = request.email,
            role     = request.role,
            rollNo   = request.rollNo,
            photoUrl = request.photoUrl
        )
    )

    @GetMapping("/profile")
    fun getProfile(@AuthenticationPrincipal uid: String) =
        ResponseEntity.ok(authService.getUserProfile(uid))

    @GetMapping("/students")
    fun getMyStudents(@AuthenticationPrincipal uid: String) =
        ResponseEntity.ok(authService.getStudentsByMentor(uid))
}

// ── Health check endpoint ──────────────────────
@RestController
class HealthController {
    @GetMapping("/health")
    fun health() = ResponseEntity.ok(mapOf("status" to "UP", "app" to "Mentora Backend"))
}
