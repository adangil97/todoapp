package mx.com.sendal.todoapp.todo.datasources

import mx.com.sendal.data.datasources.TodoDataSource
import mx.com.sendal.data.models.TodoModel
import mx.com.sendal.todoapp.todo.database.TodoDao
import mx.com.sendal.todoapp.todo.utils.toTodo
import mx.com.sendal.todoapp.todo.utils.toTodoModel

/**
 * @author Adán Castillo.
 */
class LocalTodoDataSource(
    private val todoDao: TodoDao
) : TodoDataSource {

    override suspend fun getAll(): List<TodoModel> =
        todoDao.getAll().map {
            it.toTodoModel()
        }

    override suspend fun create(todoModel: TodoModel) =
        todoDao.create(todoModel.toTodo())

    override suspend fun update(todoModel: TodoModel): TodoModel {
        todoDao.create(todoModel.toTodo())
        return todoModel
    }

    override suspend fun delete(todoModel: TodoModel) =
        todoDao.delete(todoModel.toTodo())
}