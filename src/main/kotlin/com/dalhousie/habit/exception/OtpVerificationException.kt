package com.dalhousie.habit.exception

sealed class OtpVerificationException(override val message: String) : RuntimeException(message) {
    data class EmailNotFound(val email: String) :
        OtpVerificationException("User with email: $email has not requested for resetting password")

    data class OtpExpired(val email: String) :
        OtpVerificationException("OTP for user with email: $email has expired")

    data class OtpNotMatching(val email: String) :
        OtpVerificationException("OTP for user with email: $email does not match")
}
