package com.dalhousie.habit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "user")
data class User(
    @Id
    val id: String? = null,

    @Field(name = "profile_pic_id")
    val profilePicId: Int = 0,

    @Field(name = "username")
    val userName: String,

    @Field(name = "email")
    val email: String,

    @Field(name = "password")
    val userPassword: String
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun getPassword(): String = userPassword

    override fun getUsername(): String = email

    fun toPublicUser(habits: List<Habit> = emptyList()): PublicUser = PublicUser(
        id = id ?: "",
        profilePicId = profilePicId,
        userName = userName,
        email = email,
        habits = habits
    )
}
