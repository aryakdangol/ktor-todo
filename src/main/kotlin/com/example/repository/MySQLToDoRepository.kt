package com.example.repository

import com.example.db.DatabaseFactory
import com.example.dto.ToDoDTO
import com.example.models.ToDo

class MySQLToDoRepository: ToDoRepository {

    private val database = DatabaseFactory()

    override fun getAllToDos(): List<ToDo> {
        return database.getAllTodos().map{ ToDo(it.id, it.title, it.done) }
    }

    override fun getToDo(id: Int): ToDo? {
        return database.getToDo(id)?.let { ToDo(it.id, it.title, it.done) }
    }

    override fun addToDo(params: ToDoDTO): ToDo {
        return database.addToDo(params)
    }

    override fun removeToDo(id: Int): Boolean {
        return database.removeToDo(id)
    }

    override fun updateToDo(id: Int, params: ToDoDTO): ToDo? {
        return database.updateToDo(id, params)
    }
}