package com.dalhousie.habit.response

import com.dalhousie.habit.model.Habit
import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS

data class SingleHabitResponse(
    override val resultType: ResultType,
    override val data: Habit,
    override val message: String
) : ResponseBody<Habit> {

    companion object {
        fun success(habit: Habit, message: String): SingleHabitResponse =
            SingleHabitResponse(SUCCESS, habit, message)
    }
}
