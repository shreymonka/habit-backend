package com.dalhousie.habit.service

import com.dalhousie.habit.repository.HabitRepository
import com.dalhousie.habit.repository.UserRepository
import com.dalhousie.habit.response.SearchUserResponse
import org.springframework.stereotype.Service

interface UserService {
    fun searchUsers(query: String): SearchUserResponse
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val habitRepository: HabitRepository
) : UserService {

    override fun searchUsers(query: String): SearchUserResponse {
        val publicUsersList = userRepository
            .findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query)
            .map {
                it.toPublicUser(habitRepository.findAllByUserId(it.id.orEmpty()))
            }
        return SearchUserResponse.success(publicUsersList)
    }
}
