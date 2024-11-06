package com.dalhousie.habit.exception

class UserAlreadyRegisteredException(email: String) :
    RuntimeException("User already registered with email: $email")
