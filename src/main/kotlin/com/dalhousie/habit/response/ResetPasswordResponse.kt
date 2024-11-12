package com.dalhousie.habit.response

import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.RESET_PASSWORD_SUCCESS

data class ResetPasswordResponse(
    override val resultType: ResponseBody.ResultType,
    override val data: Boolean,
    override val message: String
): ResponseBody<Boolean>{
    companion object{
        fun success(): ResetPasswordResponse =
            ResetPasswordResponse(SUCCESS, true, RESET_PASSWORD_SUCCESS)
    }
}
