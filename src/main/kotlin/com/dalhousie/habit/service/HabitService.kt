package com.dalhousie.habit.service

import com.dalhousie.habit.exception.HabitAlreadyAddedException
import com.dalhousie.habit.exception.HabitNotFoundException
import com.dalhousie.habit.repository.HabitRepository
import com.dalhousie.habit.request.AddEditHabitRequest
import com.dalhousie.habit.response.BooleanResponseBody
import com.dalhousie.habit.response.GetHabitsResponse
import com.dalhousie.habit.response.SingleHabitResponse
import com.dalhousie.habit.util.Constants.ADD_HABIT_SUCCESS
import com.dalhousie.habit.util.Constants.DELETE_HABIT_SUCCESS
import com.dalhousie.habit.util.Constants.EDIT_HABIT_SUCCESS
import org.springframework.stereotype.Service

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
}

@Service
class HabitServiceImpl(
    private val habitRepository: HabitRepository
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
        if (habitRepository.existsByNameAndUserId(request.name, userId))
            throw HabitAlreadyAddedException(request.name)

        val habit = habitRepository.findById(request.id.orEmpty()).let {
            if (it.isEmpty)
                throw HabitNotFoundException(request.name)
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
}
