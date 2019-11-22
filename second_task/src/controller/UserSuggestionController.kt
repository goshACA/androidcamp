package com.art.task.controller

import com.art.task.db.entity.User
import com.art.task.db.recentUserSearchResults
import com.art.task.userList

class UserSuggestionController : SuggestionController<User> {
    override fun searchInput(input: String): ArrayList<String> {
        val result = ArrayList<String>()
        for (user in userList) {
            if (user.userName.contains(input) || user.userSurname.contains(input))
                result.add(user.userLogin)
        }
        return result
    }

    override fun recent(): Set<String> {
        return recentUserSearchResults
    }

    override fun getItem(input: String): User? {
        val res = userList.find { user -> user.userLogin == input }
        if (res != null) {
            recentUserSearchResults.add(res.userLogin)
        }
        return res
    }
}