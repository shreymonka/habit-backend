package com.dalhousie.habit.repository

import com.dalhousie.habit.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean
}
