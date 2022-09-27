package com.example.repository

import com.example.dto.ToDoDTO
import com.example.models.ToDo

interface ToDoRepository {

    fun getAllToDos(): List<ToDo>

    fun getToDo(id: Int): ToDo?

    fun addToDo(params: ToDoDTO): ToDo

    fun removeToDo(id: Int): Boolean

    fun updateToDo(id: Int, params: ToDoDTO): ToDo?

}