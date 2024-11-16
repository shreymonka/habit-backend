package com.dalhousie.habit.util.controlleradvice

import com.dalhousie.habit.exception.HabitAlreadyAddedException
import com.dalhousie.habit.exception.HabitNotFoundException
import com.dalhousie.habit.response.BooleanResponseBody
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object HabitControllerAdvice {

    private val logger = LoggerFactory.getLogger(HabitControllerAdvice::class.java)

    fun handleHabitAlreadyAddedException(exception: HabitAlreadyAddedException): ResponseEntity<BooleanResponseBody> {
        logger.error("Habit already added: ${exception.message}")
        val response = BooleanResponseBody.failure(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    fun handleHabitNotFoundException(exception: HabitNotFoundException): ResponseEntity<BooleanResponseBody> {
        logger.error("Habit not found: ${exception.message}")
        val response = BooleanResponseBody.failure(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }
}
