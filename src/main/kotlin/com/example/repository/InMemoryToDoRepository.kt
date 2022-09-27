package com.example.repository

import com.example.dto.ToDoDTO
import com.example.models.ToDo

class InMemoryToDoRepository : ToDoRepository {
    private val todos = mutableListOf<ToDo>(
        ToDo(1, "I wake up", true),
        ToDo(2, "I shit", true),
        ToDo(3, "I get out of bed", false)
    )
    override fun getAllToDos(): List<ToDo> {
        return todos
    }

    override fun getToDo(id: Int): ToDo? {
        return todos.firstOrNull{it.id == id}
    }

    override fun addToDo(params: ToDoDTO): ToDo {
        val todo = ToDo(
            id = todos.size + 1,
            title = params.title,
            done = params.done
        )
        todos.add(todo)
        return todos[todos.size -1]
    }

    override fun removeToDo(id: Int): Boolean {
       return todos.removeIf{it.id == id}
    }

    override fun updateToDo(id: Int, params: ToDoDTO): ToDo? {
        val todo = todos.firstOrNull{it.id == id} ?: return null
        todo.title = params.title
        todo.done = params.done

        return todo

    }
}