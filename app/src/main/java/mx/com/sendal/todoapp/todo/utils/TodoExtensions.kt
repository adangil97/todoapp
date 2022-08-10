package mx.com.sendal.todoapp.todo.utils

import mx.com.sendal.data.models.TodoModel
import mx.com.sendal.todoapp.todo.models.Todo

/**
 * @author Adán Castillo.
 */

fun Todo.toTodoModel(): TodoModel =
    TodoModel(
        id,
        isComplete,
        isFavorite,
        description,
        date
    )

fun TodoModel.toTodo(): Todo =
    Todo(
        id,
        isComplete,
        isFavorite,
        description,
        date
    )