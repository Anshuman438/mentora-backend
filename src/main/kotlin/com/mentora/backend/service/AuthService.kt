package com.mentora.backend.service

import com.mentora.backend.model.User
import com.mentora.backend.model.UserRole
import com.mentora.backend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(private val userRepository: UserRepository) {

    fun registerOrUpdateUser(
        uid: String,
        name: String,
        email: String,
        role: String,
        rollNo: String = "",
        photoUrl: String = ""
    ): User {
        val existing = userRepository.findById(uid)
        return if (existing.isPresent) {
            userRepository.save(
                existing.get().copy(
                    role     = UserRole.valueOf(role.uppercase()),
                    rollNo   = rollNo,
                    photoUrl = photoUrl
                )
            )
        } else {
            userRepository.save(
                User(
                    uid      = uid,
                    name     = name,
                    email    = email,
                    role     = UserRole.valueOf(role.uppercase()),
                    rollNo   = rollNo,
                    photoUrl = photoUrl
                )
            )
        }
    }

    fun getUserProfile(uid: String): User =
        userRepository.findById(uid)
            .orElseThrow { NoSuchElementException("User $uid not found") }

    fun getStudentsByMentor(mentorUid: String): List<User> =
        userRepository.findByMentorUid(mentorUid)
}
