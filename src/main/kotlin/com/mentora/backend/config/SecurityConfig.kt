package com.mentora.backend.config

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class FirebaseAuthFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val idToken = authHeader.removePrefix("Bearer ").trim()
            try {
                val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
                val uid = decodedToken.uid

                val auth = UsernamePasswordAuthenticationToken(
                    uid, null,
                    listOf(SimpleGrantedAuthority("ROLE_USER"))
                )
                SecurityContextHolder.getContext().authentication = auth
            } catch (e: FirebaseAuthException) {
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }
}

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val firebaseAuthFilter: FirebaseAuthFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/auth/register").permitAll()
                    .requestMatchers("/actuator/health").permitAll()
                    .requestMatchers("/health").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                firebaseAuthFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}
