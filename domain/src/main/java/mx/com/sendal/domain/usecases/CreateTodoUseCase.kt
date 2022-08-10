package mx.com.sendal.domain.usecases

import mx.com.sendal.data.models.TodoModel
import mx.com.sendal.data.repositories.TodoRepository

/**
 * @author Adán Castillo.
 */
class CreateTodoUseCase(
    private val todoRepository: TodoRepository
) {

    suspend operator fun invoke(todoModel: TodoModel) = todoRepository.create(todoModel)
}