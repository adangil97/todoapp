package mx.com.sendal.todoapp.todo.presentation.delete

import mx.com.sendal.todoapp.todo.models.Todo

/**
 * @author Adán Castillo.
 */
sealed class TodoDeleteEvent {

    data class OnDeleteTodoDelete(val todo: Todo) : TodoDeleteEvent()

    object OnClearState : TodoDeleteEvent()
}