package com.dalhousie.habit.response

import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.OTP_VERIFICATION_SUCCESS

data class OtpVerificationResponse(
    override val resultType: ResponseBody.ResultType,
    override val data: Boolean,
    override val message: String
) : ResponseBody<Boolean> {

    companion object {
        fun success(): OtpVerificationResponse =
            OtpVerificationResponse(SUCCESS, true, OTP_VERIFICATION_SUCCESS)
    }
}
