package com.dalhousie.habit.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class OtpVerificationRequest(
    @field:NotBlank(message = "OTP cannot be blank")
    @field:Size(min = 6, max = 6, message = "OTP cannot be blank")
    val otp: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String
)
