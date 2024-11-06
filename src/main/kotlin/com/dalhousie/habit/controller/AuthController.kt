package com.dalhousie.habit.controller

import com.dalhousie.habit.request.ForgotPasswordRequest
import com.dalhousie.habit.request.LoginRequest
import com.dalhousie.habit.request.RegisterRequest
import com.dalhousie.habit.response.ForgotPasswordResponse
import com.dalhousie.habit.response.LoginResponse
import com.dalhousie.habit.response.RegisterResponse
import com.dalhousie.habit.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<RegisterResponse> {
        val body = authService.registerUser(registerRequest)
        return ResponseEntity(body, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val body = authService.loginUser(loginRequest)
        return ResponseEntity(body, HttpStatus.OK)
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(
        @Valid @RequestBody forgotPasswordRequest: ForgotPasswordRequest
    ): ResponseEntity<ForgotPasswordResponse> {
        val body = authService.forgotPassword(forgotPasswordRequest)
        return ResponseEntity(body, HttpStatus.OK)
    }
}
