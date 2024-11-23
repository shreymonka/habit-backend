package com.dalhousie.habit.controller

import com.dalhousie.habit.model.User
import com.dalhousie.habit.response.GetUserDataResponse
import com.dalhousie.habit.response.SearchUserResponse
import com.dalhousie.habit.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUserData(@AuthenticationPrincipal user: User): ResponseEntity<GetUserDataResponse> {
        val response = GetUserDataResponse.success(user.toPublicUser())
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search")
    fun searchUsers(@RequestParam query: String): ResponseEntity<SearchUserResponse> {
        val response = userService.searchUsers(query)
        return ResponseEntity.ok(response)
    }
}
