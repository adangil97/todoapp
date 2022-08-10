package mx.com.sendal.todoapp.todo.datasources

import mx.com.sendal.data.datasources.TodoDataSource
import mx.com.sendal.data.models.TodoModel

/**
 * @author Adán Castillo.
 */
class RemoteTodoDataSource : TodoDataSource {

    override suspend fun getAll(): List<TodoModel> {
        TODO("Not yet implemented")
    }

    override suspend fun create(todoModel: TodoModel) {
        TODO("Not yet implemented")
    }

    override suspend fun update(todoModel: TodoModel): TodoModel {
        TODO("Not yet implemented")
    }

    override suspend fun delete(todoModel: TodoModel) {
        TODO("Not yet implemented")
    }
}