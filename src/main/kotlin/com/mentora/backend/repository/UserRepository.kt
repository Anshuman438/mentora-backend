package com.mentora.backend.repository

import com.mentora.backend.model.User
import com.mentora.backend.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findByEmail(email: String): Optional<User>
    fun findByMentorUid(mentorUid: String): List<User>
    fun findByRole(role: UserRole): List<User>
}
