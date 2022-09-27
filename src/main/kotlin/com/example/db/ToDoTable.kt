package com.example.db

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object ToDoTable: Table<DBToDoEntity>("todo") {
    val id = int("id").primaryKey().bindTo { it.id  }
    val title = varchar("title").bindTo { it.title }
    val done = boolean("done").bindTo { it.done }
}

interface DBToDoEntity: Entity<DBToDoEntity>{
    companion object: Entity.Factory<DBToDoEntity>()

    val id: Int
    val title: String
    val done: Boolean
}