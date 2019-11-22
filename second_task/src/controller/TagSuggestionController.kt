package com.art.task.controller

import com.art.task.db.entity.Tag
import com.art.task.db.recentTagSearchResults
import com.art.task.tagList

class TagSuggestionController: SuggestionController<Tag> {
    override fun searchInput(input: String): ArrayList<String> {
        val result = ArrayList<String>()
        for (tag in tagList) {
            if (tag.tagName.contains(input))
                result.add(tag.tagName)
        }
        return result
    }

    override fun recent(): Set<String> {
        return recentTagSearchResults
    }

    override fun getItem(input: String): Tag? {
        val res = tagList.find { tag -> tag.tagName == input }
        if (res != null) {
            recentTagSearchResults.add(res.tagName)
        }
        return res
    }
}