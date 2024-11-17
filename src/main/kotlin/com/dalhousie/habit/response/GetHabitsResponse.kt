package com.dalhousie.habit.response

import com.dalhousie.habit.model.Habit
import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.GET_HABIT_SUCCESS

data class GetHabitsResponse(
    override val resultType: ResultType,
    override val data: Data,
    override val message: String
) : ResponseBody<GetHabitsResponse.Data> {

    data class Data(
        val habits: List<Habit>
    )

    companion object {
        fun success(habits: List<Habit>): GetHabitsResponse =
            GetHabitsResponse(SUCCESS, Data(habits), GET_HABIT_SUCCESS)
    }
}
