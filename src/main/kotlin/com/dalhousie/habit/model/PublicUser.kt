package com.dalhousie.habit.model

data class PublicUser(
    val id: String,
    val profilePicId: Int,
    val userName: String,
    val email: String,
    val habits: List<Habit>
)
