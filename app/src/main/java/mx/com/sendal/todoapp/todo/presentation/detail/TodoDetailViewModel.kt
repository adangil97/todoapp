package mx.com.sendal.todoapp.todo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.sendal.data.utils.TodoResponse
import mx.com.sendal.domain.usecases.CreateTodoUseCase
import mx.com.sendal.domain.usecases.UpdateTodoUseCase
import mx.com.sendal.todoapp.todo.models.Todo
import mx.com.sendal.todoapp.todo.utils.toTodoModel
import javax.inject.Inject

/**
 * @author Adán Castillo.
 */
@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {
    private val mViewEffect = MutableLiveData<TodoDetailState>()
    val viewEffect: LiveData<TodoDetailState> get() = mViewEffect

    fun onEvent(todoDetailEvent: TodoDetailEvent) {
        when (todoDetailEvent) {
            is TodoDetailEvent.OnCreateTodo -> onCreateTodo(todoDetailEvent.todo)
            is TodoDetailEvent.OnUpdateTodo -> onUpdateTodo(todoDetailEvent.todo)
            TodoDetailEvent.OnClearState -> mViewEffect.value = TodoDetailState.ClearState
        }
    }

    private fun onCreateTodo(todo: Todo) {
        if (todo.description.isNotEmpty()) {
            viewModelScope.launch {
                when (val response = createTodoUseCase(todo.toTodoModel())) {
                    is TodoResponse.Success -> {
                        mViewEffect.value = TodoDetailState.ShowCreateTodo
                    }
                    is TodoResponse.Failed -> showGenericError(response.errorMessage)
                }
            }
        } else {
            mViewEffect.value = TodoDetailState.ShowDescriptionEmpty
        }
    }

    private fun onUpdateTodo(todo: Todo) {
        if (todo.description.isNotEmpty()) {
            viewModelScope.launch {
                when (val response = updateTodoUseCase(todo.toTodoModel())) {
                    is TodoResponse.Success -> {
                        mViewEffect.value = TodoDetailState.ShowUpdateTodo
                    }
                    is TodoResponse.Failed -> showGenericError(response.errorMessage)
                }
            }
        } else {
            mViewEffect.value = TodoDetailState.ShowDescriptionEmpty
        }
    }

    private fun showGenericError(msgError: String) {
        mViewEffect.value = TodoDetailState.ShowGenericError(msgError)
    }
}