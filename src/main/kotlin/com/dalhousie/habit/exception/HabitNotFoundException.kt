package com.dalhousie.habit.exception

class HabitNotFoundException(name: String) :
    RuntimeException("Habit not found: $name")
