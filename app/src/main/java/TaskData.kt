package com.example.dailytask

import java.io.Serializable

data class TaskData(
    val taskCategory: String,
    val taskDescription: String,
    val taskDate: String
) : Serializable