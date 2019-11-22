package com.art.task.controller

interface SuggestionController<T> {
    fun searchInput(input: String): ArrayList<String>
    fun recent(): Set<String>
    fun getItem(input: String): T?
}