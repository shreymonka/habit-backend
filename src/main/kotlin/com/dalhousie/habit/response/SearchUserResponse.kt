package com.dalhousie.habit.response

import com.dalhousie.habit.model.PublicUser
import com.dalhousie.habit.response.ResponseBody.ResultType
import com.dalhousie.habit.response.ResponseBody.ResultType.SUCCESS
import com.dalhousie.habit.util.Constants.SEARCH_USER_SUCCESS

data class SearchUserResponse(
    override val resultType: ResultType,
    override val data: Data,
    override val message: String
) : ResponseBody<SearchUserResponse.Data> {

    data class Data(
        val users: List<PublicUser>
    )

    companion object {
        fun success(users: List<PublicUser>): SearchUserResponse =
            SearchUserResponse(SUCCESS, Data(users), SEARCH_USER_SUCCESS)
    }
}
