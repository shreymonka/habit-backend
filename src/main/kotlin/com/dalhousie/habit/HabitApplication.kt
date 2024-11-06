package com.dalhousie.habit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HabitApplication

fun main(args: Array<String>) {
	runApplication<HabitApplication>(*args)
}
