package com.dalhousie.habit.response

import com.dalhousie.habit.model.Habit
import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.GET_HABIT_STATISTICS_SUCCESS
import java.time.LocalDate
import java.time.Month

data class HabitStatisticsResponse(
    override val resultType: ResultType,
    override val data: Data,
    override val message: String
) : ResponseBody<HabitStatisticsResponse.Data> {

    data class Data(
        val data: List<MonthYearAndStatistics>
    ) {

        data class MonthYearAndStatistics(
            val monthYear: MonthYear,
            val habitStatistics: List<HabitStatistics>
        ) {

            data class MonthYear(
                val month: String,
                val year: String
            ) {

                fun monthValue(): Int =
                    Month.valueOf(this.month.uppercase()).value
            }

            data class HabitStatistics(
                val habit: Habit,
                val completionDates: List<LocalDate>
            )
        }
    }

    companion object {
        fun success(data: List<Data.MonthYearAndStatistics>): HabitStatisticsResponse =
            HabitStatisticsResponse(SUCCESS, Data(data), GET_HABIT_STATISTICS_SUCCESS)
    }
}
