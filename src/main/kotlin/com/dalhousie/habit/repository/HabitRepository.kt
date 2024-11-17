package com.dalhousie.habit.repository

import com.dalhousie.habit.model.Habit
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HabitRepository : MongoRepository<Habit, String> {

    fun existsByNameAndUserId(name: String, userId: String): Boolean

    fun findAllByUserId(userId: String): List<Habit>

    fun findFirstByUserIdOrderByCreationDate(userId: String): Habit?
}
