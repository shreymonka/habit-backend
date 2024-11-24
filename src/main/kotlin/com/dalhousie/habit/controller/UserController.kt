package com.dalhousie.habit.controller

import com.dalhousie.habit.model.User
import com.dalhousie.habit.request.UpdatePasswordRequest
import com.dalhousie.habit.request.UpdateUsernameRequest
import com.dalhousie.habit.response.BooleanResponseBody
import com.dalhousie.habit.response.GetUserDataResponse
import com.dalhousie.habit.response.SearchUserResponse
import com.dalhousie.habit.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUserData(@AuthenticationPrincipal user: User): ResponseEntity<GetUserDataResponse> {
        val response = userService.getUserData(user)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search")
    fun searchUsers(
        @AuthenticationPrincipal user: User,
        @RequestParam query: String
    ): ResponseEntity<SearchUserResponse> {
        val response = userService.searchUsers(user.id.orEmpty(), query)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/update-username")
    fun updateUsername(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: UpdateUsernameRequest
    ): ResponseEntity<BooleanResponseBody> {
        val response = userService.updateUsername(user, request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/update-password")
    fun updatePassword(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: UpdatePasswordRequest
    ): ResponseEntity<BooleanResponseBody> {
        val response = userService.updatePassword(user, request)
        return ResponseEntity.ok(response)
    }
}
