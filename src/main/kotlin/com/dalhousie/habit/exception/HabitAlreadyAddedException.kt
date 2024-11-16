package com.dalhousie.habit.exception

class HabitAlreadyAddedException(name: String) :
    RuntimeException("Habit already added: $name")
