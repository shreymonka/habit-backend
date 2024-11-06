package com.dalhousie.habit.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ForgotPasswordRequest(
    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String
)
