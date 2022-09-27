package com.example.db

import com.example.dto.ToDoDTO
import com.example.models.ToDo
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseFactory {
    private val hostname = "localhost"
    private val databaseName = "ktor_todo"
    private val username = "root"
    private val password = ""

    private val ktormDatabse: Database

    init{
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&passoword=$password"
        ktormDatabse = Database.connect(jdbcUrl)
    }
    fun getAllTodos(): List<DBToDoEntity>{
        return ktormDatabse.sequenceOf(ToDoTable).toList()
    }

    fun getToDo(id : Int): DBToDoEntity? {
        return ktormDatabse.sequenceOf(ToDoTable).firstOrNull{it.id eq  id}
    }

    fun addToDo(ToDoDTO: ToDoDTO): ToDo {
       val id =  ktormDatabse.insertAndGenerateKey(ToDoTable){
            set(ToDoTable.title, ToDoDTO.title)
            set(ToDoTable.done, ToDoDTO.done)
        } as Int
        return ToDo(id, ToDoDTO.title, ToDoDTO.done)
    }

    fun updateToDo(id: Int, params: ToDoDTO): ToDo?{
        val updatedRows = ktormDatabse.update(ToDoTable){
            set(ToDoTable.title, params.title)
            set(ToDoTable.done, params.done)
            where{
                it.id eq id
            }
        }
        if (updatedRows > 0)
        {
            return getToDo(id)?.let { ToDo(it.id, it.title, it.done) }
        }
        else{
            return null
        }
    }

    fun removeToDo(id: Int): Boolean{
        val deletedRows = ktormDatabse.delete(ToDoTable){
            it.id eq id
        }
        return deletedRows > 0
    }
}