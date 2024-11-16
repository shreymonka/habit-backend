package com.dalhousie.habit.util.controlleradvice

import com.dalhousie.habit.exception.HabitAlreadyAddedException
import com.dalhousie.habit.exception.HabitNotFoundException
import com.dalhousie.habit.exception.InvalidPasswordException
import com.dalhousie.habit.exception.OtpVerificationException
import com.dalhousie.habit.exception.UserAlreadyRegisteredException
import com.dalhousie.habit.exception.UserNotFoundException
import com.dalhousie.habit.response.BooleanResponseBody
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalControllerAdvice {

    // region: GeneralControllerAdvice
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationExceptions(exception: ConstraintViolationException): ResponseEntity<BooleanResponseBody> =
        GeneralControllerAdvice.handleValidationExceptions(exception)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ResponseEntity<BooleanResponseBody> =
        GeneralControllerAdvice.handleMethodArgumentNotValid(exception)

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(exception: Exception): ResponseEntity<BooleanResponseBody> =
        GeneralControllerAdvice.handleGlobalException(exception)
    // end region

    // Region: AuthControllerAdvice
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<BooleanResponseBody> =
        AuthControllerAdvice.handleUserNotFoundException(exception)

    @ExceptionHandler(UserAlreadyRegisteredException::class)
    fun handleUserAlreadyRegisteredException(exception: UserAlreadyRegisteredException): ResponseEntity<BooleanResponseBody> =
        AuthControllerAdvice.handleUserAlreadyRegisteredException(exception)

    @ExceptionHandler(InvalidPasswordException::class)
    fun handleInvalidPasswordException(exception: InvalidPasswordException): ResponseEntity<BooleanResponseBody> =
        AuthControllerAdvice.handleInvalidPasswordException(exception)

    @ExceptionHandler(OtpVerificationException::class)
    fun handleOtpVerificationException(exception: OtpVerificationException): ResponseEntity<BooleanResponseBody> =
        AuthControllerAdvice.handleOtpVerificationException(exception)
    // end region

    // Region: HabitControllerAdvice
    @ExceptionHandler(HabitAlreadyAddedException::class)
    fun handleHabitAlreadyAddedException(exception: HabitAlreadyAddedException): ResponseEntity<BooleanResponseBody> =
        HabitControllerAdvice.handleHabitAlreadyAddedException(exception)

    @ExceptionHandler(HabitNotFoundException::class)
    fun handleHabitNotFoundException(exception: HabitNotFoundException): ResponseEntity<BooleanResponseBody> =
        HabitControllerAdvice.handleHabitNotFoundException(exception)
    // end region
}
