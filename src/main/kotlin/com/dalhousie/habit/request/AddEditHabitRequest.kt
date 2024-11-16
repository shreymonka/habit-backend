package com.dalhousie.habit.request

import com.dalhousie.habit.model.Habit
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDateTime

data class AddEditHabitRequest(
    val id: String? = null,

    @field:NotBlank(message = "Habit name cannot be blank")
    val name: String,

    @field:NotEmpty(message = "Schedule cannot be empty")
    val schedule: List<String>
) {

    fun toHabit(userId: String) = Habit(
        id = id,
        userId = userId,
        name = name,
        creationDate = LocalDateTime.now(),
        schedule = schedule
    )
}
