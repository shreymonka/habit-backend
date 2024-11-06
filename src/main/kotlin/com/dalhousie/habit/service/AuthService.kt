package com.dalhousie.habit.service

import com.dalhousie.habit.exception.InvalidPasswordException
import com.dalhousie.habit.exception.UserAlreadyRegisteredException
import com.dalhousie.habit.exception.UserNotFoundException
import com.dalhousie.habit.model.Otp
import com.dalhousie.habit.repository.OtpRepository
import com.dalhousie.habit.repository.UserRepository
import com.dalhousie.habit.request.ForgotPasswordRequest
import com.dalhousie.habit.request.LoginRequest
import com.dalhousie.habit.request.RegisterRequest
import com.dalhousie.habit.response.ForgotPasswordResponse
import com.dalhousie.habit.response.LoginResponse
import com.dalhousie.habit.response.LoginResponse.Data
import com.dalhousie.habit.response.RegisterResponse
import com.dalhousie.habit.util.EmailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface AuthService {

    /**
     * Register a user into the application
     * @param request Request model for registering the user
     * @return [RegisterResponse] object
     */
    fun registerUser(request: RegisterRequest): RegisterResponse

    /**
     * Login the user into the application
     * @param request User details to login
     * @return [LoginResponse] object
     */
    fun loginUser(request: LoginRequest): LoginResponse

    /**
     * Handles forgot password request and sends OTP to the requesting email address
     * @param request Request model containing email to make a password change request
     * @return [ForgotPasswordResponse] object
     */
    fun forgotPassword(request: ForgotPasswordRequest): ForgotPasswordResponse
}

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val otpRepository: OtpRepository,
    private val emailSender: EmailSender
) : AuthService {

    override fun registerUser(request: RegisterRequest): RegisterResponse {
        if (userRepository.existsByEmail(request.email))
            throw UserAlreadyRegisteredException(request.email)

        val user = request.toUser(passwordEncoder)
        userRepository.save(user)
        return RegisterResponse.success()
    }

    override fun loginUser(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException(request.email)
        if (!passwordEncoder.matches(request.password, user.password))
            throw InvalidPasswordException(request.email)

        val token = jwtService.generateToken(user)
        return LoginResponse.success(Data(token))
    }

    override fun forgotPassword(request: ForgotPasswordRequest): ForgotPasswordResponse {
        val email = request.email
        if (!userRepository.existsByEmail(email))
            throw UserNotFoundException(email)

        otpRepository.findByEmail(email)?.let(otpRepository::delete)
        val otp = Otp.getOtpModel(email)
        otpRepository.save(otp).also {
            emailSender.sendOtpToMail(it.otp, it.email)
        }
        return ForgotPasswordResponse.success()
    }
}
