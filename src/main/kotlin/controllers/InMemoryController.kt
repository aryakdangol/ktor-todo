package controllers

import com.example.dto.ToDoDTO
import com.example.repository.InMemoryToDoRepository
import com.example.repository.ToDoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.inMemoryController(){
    route("/api/v1") {
        val repository: ToDoRepository = InMemoryToDoRepository()

        get("/todos"){
            val res = repository.getAllToDos()
            call.respond(HttpStatusCode.OK,res)
        }
        get("/todos/{id?}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null){
                val todo = repository.getToDo(id)
                if (todo == null){
                    call.respond(HttpStatusCode.NotFound, "Item not found")
                }
                else{
                    call.respond(HttpStatusCode.OK, todo)
                }
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "invalid id")
                return@get
            }
        }
        post("/todos"){
            val body = call.receive<ToDoDTO>()
            val res = repository.addToDo(body)
            call.respond(HttpStatusCode.Created, res)
        }
        put("/todos/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null){
                call.respond(HttpStatusCode.BadRequest, "invalid id")
                return@put
            }
            val body = call.receive<ToDoDTO>()
            val res = repository.updateToDo(id, body)
            if (res == null){
                call.respond(HttpStatusCode.NotFound, "Id Not found")
                return@put
            }
            else{
                call.respond(HttpStatusCode.OK, res)
            }
        }
        delete("/todos/{id?}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null){
                call.respond(HttpStatusCode.BadRequest, "Invalid Id!")
                return@delete
            }
            val remove = repository.removeToDo(id)
            if (remove){
                call.respond(HttpStatusCode.Gone, "Deleted Sucessfully")
            }
            else{
                call.respond(HttpStatusCode.NotFound, "Id Not Found")
            }
        }
    }

}