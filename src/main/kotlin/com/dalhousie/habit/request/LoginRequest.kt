package com.dalhousie.habit.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String,

    @field:NotBlank(message = "Password is mandatory")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String
)
