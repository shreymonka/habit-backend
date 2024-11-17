package com.dalhousie.habit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDate

@Document(collection = "habit_tracker")
data class HabitTracker(
    @Id
    val id: String? = null,

    @Field(name = "user_id")
    val userId: String,

    @Field(name = "habit_id")
    val habitId: String,

    @Field(name = "completion_dates")
    val completionDates: List<LocalDate>,
)
