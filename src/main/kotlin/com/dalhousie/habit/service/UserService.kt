package com.dalhousie.habit.service

import com.dalhousie.habit.exception.InvalidPasswordException
import com.dalhousie.habit.model.User
import com.dalhousie.habit.repository.HabitRepository
import com.dalhousie.habit.repository.UserRepository
import com.dalhousie.habit.request.UpdatePasswordRequest
import com.dalhousie.habit.request.UpdateProfilePicRequest
import com.dalhousie.habit.request.UpdateUsernameRequest
import com.dalhousie.habit.response.BooleanResponseBody
import com.dalhousie.habit.response.GetUserDataResponse
import com.dalhousie.habit.response.SearchUserResponse
import com.dalhousie.habit.util.Constants.UPDATE_PASSWORD_SUCCESS
import com.dalhousie.habit.util.Constants.UPDATE_PROFILE_PIC_SUCCESS
import com.dalhousie.habit.util.Constants.UPDATE_USERNAME_SUCCESS
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface UserService {

    fun getUserData(user: User): GetUserDataResponse

    fun searchUsers(loggedInUserId: String, query: String): SearchUserResponse

    fun updateUsername(user: User, request: UpdateUsernameRequest): BooleanResponseBody

    fun updatePassword(user: User, request: UpdatePasswordRequest): BooleanResponseBody

    fun updateProfilePic(user: User, request: UpdateProfilePicRequest): BooleanResponseBody
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val habitRepository: HabitRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun getUserData(user: User): GetUserDataResponse {
        val habits = habitRepository.findAllByUserId(user.id.orEmpty())
        return GetUserDataResponse.success(user.toPublicUser(habits))
    }

    override fun searchUsers(loggedInUserId: String, query: String): SearchUserResponse {
        val publicUsersList = userRepository
            .findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query)
            .filter { it.id != loggedInUserId }
            .map { it.toPublicUser(habitRepository.findAllByUserId(it.id.orEmpty())) }
        return SearchUserResponse.success(publicUsersList)
    }

    override fun updateUsername(user: User, request: UpdateUsernameRequest): BooleanResponseBody {
        val updatedUser = user.copy(userName = request.userName)
        userRepository.save(updatedUser)
        return BooleanResponseBody.success(UPDATE_USERNAME_SUCCESS)
    }

    override fun updatePassword(user: User, request: UpdatePasswordRequest): BooleanResponseBody {
        if (!passwordEncoder.matches(request.oldPassword, user.userPassword))
            throw InvalidPasswordException(user.email)

        val updatedUser = user.copy(userPassword = passwordEncoder.encode(request.newPassword))
        userRepository.save(updatedUser)
        return BooleanResponseBody.success(UPDATE_PASSWORD_SUCCESS)
    }

    override fun updateProfilePic(user: User, request: UpdateProfilePicRequest): BooleanResponseBody {
        val updatedUser = user.copy(profilePicId = request.profilePicId)
        userRepository.save(updatedUser)
        return BooleanResponseBody.success(UPDATE_PROFILE_PIC_SUCCESS)
    }
}
