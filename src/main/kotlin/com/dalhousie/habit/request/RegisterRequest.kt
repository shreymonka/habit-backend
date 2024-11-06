package com.dalhousie.habit.request

import com.dalhousie.habit.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.password.PasswordEncoder

data class RegisterRequest(
    @field:NotBlank(message = "Full name cannot be blank")
    val userName: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String,

    @field:NotBlank(message = "Password is mandatory")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,
) {

    fun toUser(passwordEncoder: PasswordEncoder): User = User(
        userName = userName,
        email = email,
        userPassword = passwordEncoder.encode(password)
    )
}
