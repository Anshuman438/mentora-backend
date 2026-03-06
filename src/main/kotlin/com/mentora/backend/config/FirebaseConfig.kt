package com.mentora.backend.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream

@Configuration
class FirebaseConfig {

    @PostConstruct
    fun initFirebase() {
        if (FirebaseApp.getApps().isEmpty()) {
            val credentials = try {
                val json = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON")
                if (!json.isNullOrEmpty()) {
                    GoogleCredentials.fromStream(
                        ByteArrayInputStream(json.toByteArray())
                    )
                } else {
                    val stream = javaClass.classLoader
                        .getResourceAsStream("firebase-service-account.json")
                        ?: throw IllegalStateException("No Firebase credentials found")
                    GoogleCredentials.fromStream(stream)
                }
            } catch (e: Exception) {
                throw IllegalStateException("Firebase init failed: ${e.message}")
            }

            FirebaseApp.initializeApp(
                FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build()
            )
        }
    }
}
