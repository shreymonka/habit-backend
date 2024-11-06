package com.dalhousie.habit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import kotlin.math.pow
import kotlin.random.Random

@Document(collection = "otp")
data class Otp(
    @Id
    val id: String? = null,

    @Field(name = "email")
    val email: String,

    @Field(name = "otp")
    val otp: String,

    @Field(name = "expires")
    val expires: LocalDateTime
) {
    companion object {
        private const val OTP_DIGITS = 6
        private const val OTP_PAD_CHAR = '0'
        private const val OTP_EXPIRATION_MINUTES = 10L

        fun getOtpModel(email: String): Otp = Otp(
            email = email,
            otp = generateOTP(),
            expires = LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES)
        )

        private fun generateOTP(digits: Int = OTP_DIGITS): String {
            val min = 10.0.pow((digits - 1).toDouble()).toInt()
            val max = 10.0.pow(digits.toDouble()).toInt()
            return Random.nextInt(min, max).toString().padStart(digits, OTP_PAD_CHAR)
        }
    }
}
