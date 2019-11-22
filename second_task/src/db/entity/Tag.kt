package com.art.task.db.entity

class Tag(val id: String, val tagName: String){
    override fun toString(): String {
        return "Tag(id=$id, name=$tagName)"
    }
}