package com.dalhousie.habit.controller

import com.dalhousie.habit.request.ForgotPasswordRequest
import com.dalhousie.habit.request.LoginRequest
import com.dalhousie.habit.request.OtpVerificationRequest
import com.dalhousie.habit.request.RegisterRequest
import com.dalhousie.habit.request.ResetPasswordRequest
import com.dalhousie.habit.response.BooleanResponseBody
import com.dalhousie.habit.response.LoginResponse
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
    fun register(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<BooleanResponseBody> {
        val body = authService.registerUser(registerRequest)
        return ResponseEntity(body, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val body = authService.loginUser(loginRequest)
        return ResponseEntity(body, HttpStatus.OK)
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(@Valid @RequestBody request: ForgotPasswordRequest): ResponseEntity<BooleanResponseBody> {
        val body = authService.forgotPassword(request)
        return ResponseEntity(body, HttpStatus.OK)
    }

    @PostMapping("/verify-otp")
    fun verifyOtp(@Valid @RequestBody request: OtpVerificationRequest): ResponseEntity<BooleanResponseBody> {
        val body = authService.verifyOtp(request)
        return ResponseEntity(body, HttpStatus.OK)
    }

    @PostMapping("/reset-password")
    fun resetPassword(@Valid @RequestBody request: ResetPasswordRequest): ResponseEntity<BooleanResponseBody> {
        val body = authService.resetPassword(request)
        return ResponseEntity(body, HttpStatus.OK)
    }
}
