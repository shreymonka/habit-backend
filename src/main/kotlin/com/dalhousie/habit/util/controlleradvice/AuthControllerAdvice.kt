package com.dalhousie.habit.util.controlleradvice

import com.dalhousie.habit.exception.InvalidPasswordException
import com.dalhousie.habit.exception.OtpVerificationException
import com.dalhousie.habit.exception.UserAlreadyRegisteredException
import com.dalhousie.habit.exception.UserNotFoundException
import com.dalhousie.habit.response.BooleanResponseBody
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object AuthControllerAdvice {

    private val logger = LoggerFactory.getLogger(AuthControllerAdvice::class.java)

    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<BooleanResponseBody> {
        logger.error("User not found: ${exception.message}")
        val response = BooleanResponseBody.failure(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    fun handleUserAlreadyRegisteredException(exception: UserAlreadyRegisteredException): ResponseEntity<BooleanResponseBody> {
        logger.error("User already registered: ${exception.message}")
        val response = BooleanResponseBody.failure(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    fun handleInvalidPasswordException(exception: InvalidPasswordException): ResponseEntity<BooleanResponseBody> {
        logger.error("User password does not match: ${exception.message}")
        val response = BooleanResponseBody.failure(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    fun handleOtpVerificationException(exception: OtpVerificationException): ResponseEntity<BooleanResponseBody> {
        logger.error("Otp verification error: ${exception.message}")
        val response = BooleanResponseBody.failure(exception.message)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }
}
