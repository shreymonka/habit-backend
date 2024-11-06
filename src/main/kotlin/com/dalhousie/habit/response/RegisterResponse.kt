package com.dalhousie.habit.response

import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.REGISTER_REQUEST_SUCCESS

data class RegisterResponse(
    override val resultType: ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun success(): RegisterResponse =
            RegisterResponse(SUCCESS, true, REGISTER_REQUEST_SUCCESS)
    }
}
