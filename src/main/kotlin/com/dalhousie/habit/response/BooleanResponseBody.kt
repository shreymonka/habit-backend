package com.dalhousie.habit.response

import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.FAILURE
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS

data class BooleanResponseBody(
    override val resultType: ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun success(message: String): BooleanResponseBody =
            BooleanResponseBody(SUCCESS, true, message)

        fun failure(message: String): BooleanResponseBody =
            BooleanResponseBody(FAILURE, false, message)
    }
}
