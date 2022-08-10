package mx.com.sendal.todoapp.todo.presentation.list

import mx.com.sendal.todoapp.todo.models.Todo

/**
 * @author Adán Castillo.
 */
sealed class TodoListEvent {

    object OnGetAllTodos : TodoListEvent()

    data class OnUpdateTodo(val todo: Todo) : TodoListEvent()

    object OnClearState: TodoListEvent()
}