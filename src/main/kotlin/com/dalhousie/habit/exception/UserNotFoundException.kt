package com.dalhousie.habit.exception

class UserNotFoundException(email: String) :
    RuntimeException("User not found with email: $email")
