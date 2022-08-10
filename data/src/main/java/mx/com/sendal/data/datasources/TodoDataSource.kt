package mx.com.sendal.data.datasources

import mx.com.sendal.data.models.TodoModel

/**
 * @author Adán Castillo.
 */
interface TodoDataSource {

    suspend fun getAll(): List<TodoModel>

    suspend fun create(todoModel: TodoModel)

    suspend fun update(todoModel: TodoModel): TodoModel

    suspend fun delete(todoModel: TodoModel)
}