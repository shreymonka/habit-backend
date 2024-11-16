package com.dalhousie.habit.controller

import com.dalhousie.habit.model.User
import com.dalhousie.habit.request.AddEditHabitRequest
import com.dalhousie.habit.response.BooleanResponseBody
import com.dalhousie.habit.response.GetHabitsResponse
import com.dalhousie.habit.response.SingleHabitResponse
import com.dalhousie.habit.service.HabitService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/habit")
class HabitController(private val habitService: HabitService) {

    @PostMapping("/add-habit")
    fun addHabit(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: AddEditHabitRequest
    ): ResponseEntity<SingleHabitResponse> {
        val body = habitService.addHabit(user.id.orEmpty(), request)
        return ResponseEntity(body, HttpStatus.CREATED)
    }

    @GetMapping("/get-habits")
    fun getHabits(@AuthenticationPrincipal user: User): ResponseEntity<GetHabitsResponse> {
        val body = habitService.getHabits(user.id.orEmpty())
        return ResponseEntity(body, HttpStatus.OK)
    }

    @PostMapping("/edit-habit")
    fun editHabit(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: AddEditHabitRequest
    ): ResponseEntity<SingleHabitResponse> {
        val body = habitService.editHabit(user.id.orEmpty(), request)
        return ResponseEntity(body, HttpStatus.OK)
    }

    @DeleteMapping("/delete-habit")
    fun deleteHabit(@RequestParam id: String): ResponseEntity<BooleanResponseBody> {
        val body = habitService.deleteHabit(id)
        return ResponseEntity(body, HttpStatus.OK)
    }
}
