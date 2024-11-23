package com.dalhousie.habit.service

import com.dalhousie.habit.exception.HabitAlreadyAddedException
import com.dalhousie.habit.exception.HabitNotFoundException
import com.dalhousie.habit.model.HabitTracker
import com.dalhousie.habit.repository.HabitRepository
import com.dalhousie.habit.repository.HabitTrackerRepository
import com.dalhousie.habit.request.AddEditHabitRequest
import com.dalhousie.habit.response.BooleanResponseBody
import com.dalhousie.habit.response.GetHabitsResponse
import com.dalhousie.habit.response.HabitStatisticsResponse
import com.dalhousie.habit.response.SingleHabitResponse
import com.dalhousie.habit.response.TodayHabitsResponse
import com.dalhousie.habit.util.Constants.ADD_HABIT_SUCCESS
import com.dalhousie.habit.util.Constants.DELETE_HABIT_SUCCESS
import com.dalhousie.habit.util.Constants.EDIT_HABIT_SUCCESS
import com.dalhousie.habit.util.Constants.MARK_HABIT_AS_COMPLETE_SUCCESS
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

interface HabitService {

    /**
     * Add the habit for specific user
     * @param userId ID of the user to add habit
     * @param request [AddEditHabitRequest] object
     * @return [BooleanResponseBody] object
     */
    fun addHabit(userId: String, request: AddEditHabitRequest): SingleHabitResponse

    /**
     * Provide all the habits added for the requesting user
     * @param userId ID of the user to add habit
     * @return [GetHabitsResponse] object
     */
    fun getHabits(userId: String): GetHabitsResponse

    /**
     * Edits the requesting habit with the latest data
     * @param userId ID of the user to edit the habit
     * @param request [AddEditHabitRequest] object
     * @return [BooleanResponseBody] object
     */
    fun editHabit(userId: String, request: AddEditHabitRequest): SingleHabitResponse

    /**
     * Delete a habit with the provided id
     * @param id ID of the habit to delete
     */
    fun deleteHabit(id: String): BooleanResponseBody

    /**
     * Provides all habits of requesting user for today
     * @param userId ID of the user
     * @return [TodayHabitsResponse] object
     */
    fun getTodayHabits(userId: String): TodayHabitsResponse

    /**
     * Mark specific habit as complete for today
     * @param userId ID of the user
     * @param habitId ID of the habit
     */
    fun markHabitAsComplete(userId: String, habitId: String): BooleanResponseBody

    /**
     * Provides month and year list from first habit added till today
     * @param userId ID of the user
     */
    fun getHabitStatistics(userId: String): HabitStatisticsResponse
}

@Service
class HabitServiceImpl(
    private val habitRepository: HabitRepository,
    private val habitTrackerRepository: HabitTrackerRepository
) : HabitService {

    override fun addHabit(userId: String, request: AddEditHabitRequest): SingleHabitResponse {
        if (habitRepository.existsByNameAndUserId(request.name, userId))
            throw HabitAlreadyAddedException(request.name)

        val habit = habitRepository.save(request.toHabit(userId))
        return SingleHabitResponse.success(habit, ADD_HABIT_SUCCESS)
    }

    override fun getHabits(userId: String): GetHabitsResponse {
        val habits = habitRepository.findAllByUserId(userId)
        return GetHabitsResponse.success(habits)
    }

    override fun editHabit(userId: String, request: AddEditHabitRequest): SingleHabitResponse {
        if (!habitRepository.existsByNameAndUserId(request.name, userId))
            throw HabitNotFoundException(request.name)

        val habit = habitRepository.findById(request.id.orEmpty()).let {
            if (it.isEmpty) throw HabitNotFoundException(request.name)
            it.get()
        }
        val updatedHabit = habitRepository.save(habit.copy(name = request.name, schedule = request.schedule))
        return SingleHabitResponse.success(updatedHabit, EDIT_HABIT_SUCCESS)
    }

    override fun deleteHabit(id: String): BooleanResponseBody {
        if (!habitRepository.existsById(id))
            throw HabitNotFoundException(id)
        habitRepository.deleteById(id)
        return BooleanResponseBody.success(DELETE_HABIT_SUCCESS)
    }

    override fun getTodayHabits(userId: String): TodayHabitsResponse {
        val habits = habitRepository.findAllByUserId(userId).filter {
            it.getScheduleInDayOfWeek().contains(LocalDate.now().dayOfWeek)
        }.map {
            TodayHabitsResponse.Data.Data(
                habit = it,
                isCompleted = habitTrackerRepository.findByUserIdAndHabitId(userId, it.id.orEmpty())
                    ?.completionDates
                    ?.contains(LocalDate.now())
                    ?: false
            )
        }
        return TodayHabitsResponse.success(habits)
    }

    override fun markHabitAsComplete(userId: String, habitId: String): BooleanResponseBody {
        val habitTracker = habitTrackerRepository.findByUserIdAndHabitId(userId, habitId)
        val completionDates = buildList {
            addAll(habitTracker?.completionDates ?: listOf())
            add(LocalDate.now())
        }.distinct()
        val newHabitTracker = HabitTracker(habitTracker?.id, userId, habitId, completionDates)
        habitTrackerRepository.save(newHabitTracker)
        return BooleanResponseBody.success(MARK_HABIT_AS_COMPLETE_SUCCESS)
    }

    override fun getHabitStatistics(userId: String): HabitStatisticsResponse {
        val monthYearList = habitRepository
            .findFirstByUserIdOrderByCreationDate(userId)
            ?.let { habit ->
                val currentDate = LocalDateTime.now()
                var tempDate = habit.creationDate

                buildList {
                    while (!tempDate.isAfter(currentDate)) {
                        add(
                            HabitStatisticsResponse.Data.MonthYearAndStatistics.MonthYear(
                                month = tempDate.month.name.lowercase().replaceFirstChar { it.uppercase() },
                                year = tempDate.year.toString()
                            )
                        )
                        tempDate = tempDate.plusMonths(1)
                    }
                }
            }.orEmpty()

        val habits = habitRepository.findAllByUserId(userId)
        val habitTrackers = habitTrackerRepository.findAllByUserId(userId)

        val habitStatistics = habits.map { habit ->
            val matchingTrackers = habitTrackers.filter { it.habitId == habit.id }
            HabitStatisticsResponse.Data.MonthYearAndStatistics.HabitStatistics(
                habit = habit,
                completionDates = matchingTrackers.flatMap { it.completionDates }
            )
        }

        val data = monthYearList.map { monthYear ->
            val filteredHabits = habitStatistics.map { habitStat ->
                val filteredCompletionDates = habitStat.completionDates.filter { completionDate ->
                    completionDate.year == monthYear.year.toInt() && completionDate.month.name.equals(
                        monthYear.month,
                        ignoreCase = true
                    )
                }
                habitStat.copy(completionDates = filteredCompletionDates)
            }.filter {
                it.habit.creationDate.let { creationDate ->
                    (creationDate.year < monthYear.year.toInt()) ||
                            ((creationDate.year == monthYear.year.toInt()) &&
                                    (creationDate.month.value <= monthYear.monthValue()))
                }
            }
            HabitStatisticsResponse.Data.MonthYearAndStatistics(monthYear = monthYear, habitStatistics = filteredHabits)
        }
        return HabitStatisticsResponse.success(data)
    }
}
