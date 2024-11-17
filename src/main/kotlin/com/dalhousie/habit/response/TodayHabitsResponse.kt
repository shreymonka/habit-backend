package com.dalhousie.habit.response

import com.dalhousie.habit.model.Habit
import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.GET_TODAY_HABITS_SUCCESS

data class TodayHabitsResponse(
    override val resultType: ResultType,
    override val data: Data,
    override val message: String
) : ResponseBody<TodayHabitsResponse.Data> {

    data class Data(
        val habitAndStatusList: List<Data>
    ) {

        data class Data(
            val habit: Habit,
            val isCompleted: Boolean
        )
    }

    companion object {
        fun success(habitAndStatusList: List<Data.Data>): TodayHabitsResponse =
            TodayHabitsResponse(SUCCESS, Data(habitAndStatusList), GET_TODAY_HABITS_SUCCESS)
    }
}
