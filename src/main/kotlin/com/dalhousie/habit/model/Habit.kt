package com.dalhousie.habit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "habit")
data class Habit(
    @Id
    val id: String? = null,

    @Field(name = "user_id")
    val userId: String,

    @Field(name = "name")
    val name: String,

    @Field(name = "creation_date")
    val creationDate: LocalDateTime,

    @Field(name = "schedule")
    val schedule: List<String>
)
