package com.dalhousie.habit.request

import jakarta.validation.constraints.NotBlank

data class UpdateUsernameRequest(
    @field:NotBlank(message = "Full name cannot be blank")
    val userName: String,
)
