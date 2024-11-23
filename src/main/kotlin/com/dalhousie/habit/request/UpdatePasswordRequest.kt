package com.dalhousie.habit.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdatePasswordRequest(
    @field:NotBlank(message = "Password is mandatory")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val oldPassword: String,

    @field:NotBlank(message = "Password is mandatory")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val newPassword: String
)
