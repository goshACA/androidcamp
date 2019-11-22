package com.art.task

import com.art.task.controller.CommandProcessor
import com.art.task.controller.SuggestionController
import com.art.task.controller.TagSuggestionController
import com.art.task.controller.UserSuggestionController
import com.art.task.db.entity.Tag
import com.art.task.db.entity.User

fun main() {
    val userSuggestionController: SuggestionController<User> = UserSuggestionController()
    val tagSuggestionController: SuggestionController<Tag> = TagSuggestionController()
    val commandProcessor = CommandProcessor(userSuggestionController, tagSuggestionController)
    while (true) {
        commandProcessor.inputProcessor(readLine())
    }
}