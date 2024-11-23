package com.dalhousie.habit.response

import com.dalhousie.habit.model.PublicUser
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.GET_USER_DATA_SUCCESS

data class GetUserDataResponse(
    override val resultType: ResponseBody.ResultType,
    override val data: PublicUser,
    override val message: String
) : ResponseBody<PublicUser> {

    companion object {
        fun success(publicUser: PublicUser): GetUserDataResponse =
            GetUserDataResponse(SUCCESS, publicUser, GET_USER_DATA_SUCCESS)
    }
}
