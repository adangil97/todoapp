package mx.com.sendal.todoapp.todo.presentation.delete

/**
 * @author Adán Castillo.
 */
sealed class TodoDeleteState {

    object ShowDeleteTodoDelete : TodoDeleteState()

    data class ShowGenericError(
        val errorMessage: String
    ) : TodoDeleteState()

    object ClearState: TodoDeleteState()
}
