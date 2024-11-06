package com.dalhousie.habit.exception

class InvalidPasswordException(email: String) :
    RuntimeException("Invalid password provided for email: $email")
