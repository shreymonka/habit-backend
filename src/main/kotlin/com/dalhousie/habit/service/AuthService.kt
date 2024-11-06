package com.dalhousie.habit.service

import com.dalhousie.habit.exception.InvalidPasswordException
import com.dalhousie.habit.exception.UserAlreadyRegisteredException
import com.dalhousie.habit.exception.UserNotFoundException
import com.dalhousie.habit.repository.UserRepository
import com.dalhousie.habit.request.LoginRequest
import com.dalhousie.habit.request.RegisterRequest
import com.dalhousie.habit.response.LoginResponse
import com.dalhousie.habit.response.LoginResponse.Data
import com.dalhousie.habit.response.RegisterResponse
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
}

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
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
}
