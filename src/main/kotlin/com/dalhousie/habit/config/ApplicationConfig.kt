package com.dalhousie.habit.config

import com.dalhousie.habit.exception.UserNotFoundException
import com.dalhousie.habit.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationConfig(private val repository: UserRepository) {

    @Bean
    fun authenticationProvider(): AuthenticationProvider = DaoAuthenticationProvider().apply {
        setUserDetailsService(getUserDetailsService())
        setPasswordEncoder(getPasswordEncoder())
    }

    @Bean
    fun getUserDetailsService(): UserDetailsService = UserDetailsService { username ->
        repository.findByEmail(username) ?: run { throw UserNotFoundException(username) }
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun getAuthenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}
