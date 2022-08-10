package mx.com.sendal.todoapp.todo.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.sendal.data.utils.TodoResponse
import mx.com.sendal.domain.usecases.GetAllTodoUseCase
import mx.com.sendal.domain.usecases.UpdateTodoUseCase
import mx.com.sendal.todoapp.todo.models.Todo
import mx.com.sendal.todoapp.todo.utils.toTodo
import mx.com.sendal.todoapp.todo.utils.toTodoModel
import javax.inject.Inject

/**
 * @author Adán Castillo.
 */
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getAllTodoUseCase: GetAllTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {
    private val mViewEffect = MutableLiveData<TodoListState>()
    val viewEffect: LiveData<TodoListState> get() = mViewEffect

    fun onEvent(event: TodoListEvent) {
        when (event) {
            TodoListEvent.OnGetAllTodos -> onGetAllTodos()
            is TodoListEvent.OnUpdateTodo -> onUpdateTodo(event.todo)
            TodoListEvent.OnClearState -> mViewEffect.value = TodoListState.ClearState
        }
    }

    private fun onUpdateTodo(todo: Todo) {
        viewModelScope.launch {
            when (val response = updateTodoUseCase(todo.toTodoModel())) {
                is TodoResponse.Success -> {
                    mViewEffect.value = TodoListState.ShowUpdateTodo(response.content.toTodo())
                }
                is TodoResponse.Failed -> showGenericError(response.errorMessage)
            }
        }
    }

    private fun onGetAllTodos() {
        viewModelScope.launch {
            when (val response = getAllTodoUseCase()) {
                is TodoResponse.Success -> {
                    mViewEffect.value = TodoListState.ShowAllTodos(
                        response.content.map {
                            it.toTodo()
                        }
                    )
                }
                is TodoResponse.Failed -> showGenericError(response.errorMessage)
            }
        }
    }

    private fun showGenericError(msgError: String) {
        mViewEffect.value = TodoListState.ShowGenericError(msgError)
    }
}