package com.dalhousie.habit.response

import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.LOGIN_REQUEST_SUCCESS

data class LoginResponse(
    override val resultType: ResultType,
    override val data: Data,
    override val message: String
) : ResponseBody<LoginResponse.Data> {

    data class Data(
        val token: String
    )

    companion object {
        fun success(data: Data): LoginResponse =
            LoginResponse(SUCCESS, data, LOGIN_REQUEST_SUCCESS)
    }
}
