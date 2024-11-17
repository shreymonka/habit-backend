package com.dalhousie.habit.repository

import com.dalhousie.habit.model.HabitTracker
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HabitTrackerRepository : MongoRepository<HabitTracker, String> {

    fun findByUserIdAndHabitId(userId: String, habitId: String): HabitTracker?

    fun findAllByUserId(userId: String): List<HabitTracker>
}
