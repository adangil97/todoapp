package mx.com.sendal.domain.usecases

import mx.com.sendal.data.repositories.TodoRepository

/**
 * @author Adán Castillo.
 */
class GetAllTodoUseCase(private val todoRepository: TodoRepository) {

    suspend operator fun invoke() = todoRepository.getAll()
}