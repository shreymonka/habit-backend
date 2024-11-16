package com.dalhousie.habit.util.controlleradvice

import com.dalhousie.habit.response.BooleanResponseBody
import com.dalhousie.habit.util.Constants.GENERIC_API_ERROR_MESSAGE
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException

object GeneralControllerAdvice {

    private val logger = LoggerFactory.getLogger(GeneralControllerAdvice::class.java)

    fun handleValidationExceptions(exception: ConstraintViolationException): ResponseEntity<BooleanResponseBody> {
        val errors = exception.constraintViolations.map { it.message }.toString()
        logger.error("Constraint violation error: $errors")
        val response = BooleanResponseBody.failure(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ResponseEntity<BooleanResponseBody> {
        val errors = exception.bindingResult.fieldErrors.map { it.defaultMessage }.toString()
        logger.error("Method argument not valid error: $errors")
        val response = BooleanResponseBody.failure(errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    fun handleGlobalException(exception: Exception): ResponseEntity<BooleanResponseBody> {
        logger.error("An error occurred: ${exception.message}")
        val response = BooleanResponseBody.failure(GENERIC_API_ERROR_MESSAGE)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
