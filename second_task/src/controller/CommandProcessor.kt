package com.art.task.controller

import com.art.task.db.entity.Tag
import com.art.task.db.entity.User
import com.art.task.db.entity.Type

class CommandProcessor(
    private val userSuggestionController: SuggestionController<User>,
    private val tagSuggestionController: SuggestionController<Tag>
) {
    private var inputType = Type.NONE

    fun inputProcessor(input: String?) {
        when {
            input == null || input.isEmpty() -> {
                println(
                    """
                   1) You can search users and hashtags
                      Note: hashtag should start with # and username with @
                   2) After that you can get more information about user or hashtag you are interested in
                      Note: type username or hashtag
                   3) You can get recently searched hashatgs or usernames
                      Note: type @ for usernames or # for hastags
                """.trimIndent()
                )
                inputType = Type.NONE
            }
            input.startsWith('#') && input.length > 1 -> {
                println("Result: ${tagSuggestionController.searchInput(input.substring(1))}")
                inputType = Type.TAG
            }
            input == "#" -> {
                println("Result: ${tagSuggestionController.recent()}")
                inputType = Type.NONE
            }
            input.startsWith('@') && input.length > 1 -> {
                println("Result: ${userSuggestionController.searchInput(input.substring(1))}")
                inputType = Type.USER
            }
            input == "@" -> {
                println("Result:  ${userSuggestionController.recent()}")
                inputType = Type.NONE
            }
            inputType == Type.USER -> {
                val res = userSuggestionController.getItem(input)
                if (res == null)
                    println("Selected user isn't found")
                else
                    println("Selected user: ${userSuggestionController.getItem(input)}")
            }
            inputType == Type.TAG -> {
                val res = tagSuggestionController.getItem(input)
                if (res == null)
                    println("Selected user isn't found")
                else
                    println("Selected user: ${tagSuggestionController.getItem(input)}")
            }
            else -> {
                println("Please start with @ or #") // if user tries to find a hashtag/username without search
            }

        }
    }
}
