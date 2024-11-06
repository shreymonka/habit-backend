package com.dalhousie.habit.util.controlleradvice

import com.dalhousie.habit.exception.InvalidPasswordException
import com.dalhousie.habit.exception.OtpVerificationException
import com.dalhousie.habit.exception.UserAlreadyRegisteredException
import com.dalhousie.habit.exception.UserNotFoundException
import com.dalhousie.habit.response.ErrorResponseBody
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalControllerAdvice {

    // region: GeneralControllerAdvice
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationExceptions(exception: ConstraintViolationException): ResponseEntity<ErrorResponseBody> =
        GeneralControllerAdvice.handleValidationExceptions(exception)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponseBody> =
        GeneralControllerAdvice.handleMethodArgumentNotValid(exception)

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(exception: Exception): ResponseEntity<ErrorResponseBody> =
        GeneralControllerAdvice.handleGlobalException(exception)
    // end region

    // Region: AuthControllerAdvice
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<ErrorResponseBody> =
        AuthControllerAdvice.handleUserNotFoundException(exception)

    @ExceptionHandler(UserAlreadyRegisteredException::class)
    fun handleUserAlreadyRegisteredException(exception: UserAlreadyRegisteredException): ResponseEntity<ErrorResponseBody> =
        AuthControllerAdvice.handleUserAlreadyRegisteredException(exception)

    @ExceptionHandler(InvalidPasswordException::class)
    fun handleInvalidPasswordException(exception: InvalidPasswordException): ResponseEntity<ErrorResponseBody> =
        AuthControllerAdvice.handleInvalidPasswordException(exception)

    @ExceptionHandler(OtpVerificationException::class)
    fun handleOtpVerificationException(exception: OtpVerificationException): ResponseEntity<ErrorResponseBody> =
        AuthControllerAdvice.handleOtpVerificationException(exception)
    // end region
}
