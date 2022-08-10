package mx.com.sendal.todoapp.todo.presentation.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.sendal.data.utils.TodoResponse
import mx.com.sendal.domain.usecases.DeleteTodoUseCase
import mx.com.sendal.todoapp.todo.models.Todo
import mx.com.sendal.todoapp.todo.utils.toTodoModel
import javax.inject.Inject

/**
 * @author Adán Castillo.
 */
@HiltViewModel
class TodoDeleteViewModel @Inject constructor(
    private val deleteTodoUseCase: DeleteTodoUseCase,

) : ViewModel() {
    private val mViewEffect = MutableLiveData<TodoDeleteState>()
    val viewEffect: LiveData<TodoDeleteState> get() = mViewEffect

    fun onEvent(event: TodoDeleteEvent) {
        when (event) {
            is TodoDeleteEvent.OnDeleteTodoDelete -> onDeleteTodo(event.todo)
            TodoDeleteEvent.OnClearState -> mViewEffect.value = TodoDeleteState.ClearState
        }
    }

    private fun onDeleteTodo(todo: Todo) {
        viewModelScope.launch {
            when (val response = deleteTodoUseCase(todo.toTodoModel())) {
                is TodoResponse.Success -> {
                    mViewEffect.value = TodoDeleteState.ShowDeleteTodoDelete
                }
                is TodoResponse.Failed -> showGenericError(response.errorMessage)
            }
        }
    }

    private fun showGenericError(msgError: String) {
        mViewEffect.value = TodoDeleteState.ShowGenericError(msgError)
    }
}