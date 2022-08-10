package mx.com.sendal.todoapp.todo.presentation.list

import mx.com.sendal.todoapp.todo.models.Todo

/**
 * @author Adán Castillo.
 */
sealed class TodoListState {

    data class ShowAllTodos(val list: List<Todo>) : TodoListState()

    data class ShowGenericError(
        val errorMessage: String
    ) : TodoListState()

    data class ShowUpdateTodo(val todo: Todo) : TodoListState()

    object ClearState : TodoListState()
}