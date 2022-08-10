package mx.com.sendal.todoapp.todo.presentation.detail

/**
 * @author Adán Castillo.
 */
sealed class TodoDetailState {

    object ShowCreateTodo : TodoDetailState()

    object ShowUpdateTodo : TodoDetailState()

    object ShowDescriptionEmpty : TodoDetailState()

    data class ShowGenericError(
        val errorMessage: String
    ) : TodoDetailState()

    object ClearState : TodoDetailState()
}
