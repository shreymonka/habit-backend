package com.dalhousie.habit.response

import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.FORGOT_PASSWORD_REQUEST_SUCCESS

data class ForgotPasswordResponse(
    override val resultType: ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun success(): ForgotPasswordResponse =
            ForgotPasswordResponse(SUCCESS, true, FORGOT_PASSWORD_REQUEST_SUCCESS)
    }
}
