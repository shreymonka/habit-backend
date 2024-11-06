package com.dalhousie.habit.util.controlleradvice

import com.dalhousie.habit.response.ErrorResponseBody
import com.dalhousie.habit.util.Constants.GENERIC_API_ERROR_MESSAGE
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException

object GeneralControllerAdvice {

    private val logger = LoggerFactory.getLogger(GeneralControllerAdvice::class.java)

    fun handleValidationExceptions(exception: ConstraintViolationException): ResponseEntity<ErrorResponseBody> {
        val errors = exception.constraintViolations.map { it.message }.toString()
        logger.error("Constraint violation error: $errors")
        val response = ErrorResponseBody.error(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponseBody> {
        val errors = exception.bindingResult.fieldErrors.map { it.defaultMessage }.toString()
        logger.error("Method argument not valid error: $errors")
        val response = ErrorResponseBody.error(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    fun handleGlobalException(exception: Exception): ResponseEntity<ErrorResponseBody> {
        logger.error("An error occurred: ${exception.message}")
        val response = ErrorResponseBody.error(GENERIC_API_ERROR_MESSAGE)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
