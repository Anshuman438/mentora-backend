package com.mentora.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.STUDENT,
    val rollNo: String = "",
    val department: String = "",
    val photoUrl: String = "",
    val batch: String = "",
    val semester: Int = 1,
    val phone: String = "",
    val hostelRoom: String = "",
    val mentorUid: String = "",
    val fcmToken: String = "",
    val isActive: Boolean = true
)

enum class UserRole { STUDENT, TEACHER, ADMIN }
