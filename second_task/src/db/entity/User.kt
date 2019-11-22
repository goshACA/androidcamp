package com.art.task.db.entity


class User(val id: String, val userLogin: String, val userName: String, val userSurname: String){
    override fun toString(): String {
        return  "User(id=$id, username=$userLogin, name=$userName, surname=$userSurname)"
    }

}