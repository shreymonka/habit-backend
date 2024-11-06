package com.dalhousie.habit.repository

import com.dalhousie.habit.model.Otp
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OtpRepository: MongoRepository<Otp, String> {

    fun findByEmail(email: String): Otp?
}
