package mx.com.sendal.data.repositories

import mx.com.sendal.data.datasources.TodoDataSource
import mx.com.sendal.data.models.TodoModel
import mx.com.sendal.data.utils.TodoResponse

/**
 * @author Adán Castillo.
 */
class TodoRepository(
    private val localDataSource: TodoDataSource,
    private val remoteDataSource: TodoDataSource
) {

    suspend fun getAll(): TodoResponse<List<TodoModel>> =
        TodoResponse.Success(localDataSource.getAll())

    suspend fun create(todoModel: TodoModel): TodoResponse<Unit> =
        TodoResponse.Success(localDataSource.create(todoModel))

    suspend fun update(todoModel: TodoModel): TodoResponse<TodoModel> =
        TodoResponse.Success(localDataSource.update(todoModel))

    suspend fun delete(todoModel: TodoModel): TodoResponse<Unit> =
        TodoResponse.Success(localDataSource.delete(todoModel))
}