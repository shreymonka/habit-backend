package com.dalhousie.habit.response

import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.FAILURE

data class ErrorResponseBody(
    override val resultType: ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun error(message: String): ErrorResponseBody =
            ErrorResponseBody(FAILURE, false, message)
    }
}
