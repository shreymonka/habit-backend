package com.dalhousie.habit.service

import com.dalhousie.habit.exception.UserAlreadyRegisteredException
import com.dalhousie.habit.repository.UserRepository
import com.dalhousie.habit.request.RegisterRequest
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
}

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : AuthService {

    override fun registerUser(request: RegisterRequest): RegisterResponse {
        if (userRepository.existsByEmail(request.email))
            throw UserAlreadyRegisteredException(request.email)

        val user = request.toUser(passwordEncoder)
        userRepository.save(user)
        return RegisterResponse.success()
    }
}
