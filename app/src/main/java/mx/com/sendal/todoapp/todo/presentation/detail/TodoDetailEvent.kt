package mx.com.sendal.todoapp.todo.presentation.detail

import mx.com.sendal.todoapp.todo.models.Todo

/**
 * @author Adán Castillo.
 */
sealed class TodoDetailEvent {

    data class OnCreateTodo(val todo: Todo) : TodoDetailEvent()

    data class OnUpdateTodo(val todo: Todo) : TodoDetailEvent()

    object OnClearState: TodoDetailEvent()
}
