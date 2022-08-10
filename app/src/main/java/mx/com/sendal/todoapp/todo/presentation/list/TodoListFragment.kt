package mx.com.sendal.todoapp.todo.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mx.com.sendal.todoapp.R
import mx.com.sendal.todoapp.databinding.FragmentTodoListBinding
import mx.com.sendal.todoapp.todo.models.Todo

@AndroidEntryPoint
class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private val viewModel: TodoListViewModel by viewModels()
    private var mBinding: FragmentTodoListBinding? = null
    private val binding get() = requireNotNull(mBinding)
    private val todoListAdapter = TodoListAdapter(mutableListOf()) { item, isEdit ->
        if (isEdit) {
            val action = TodoListFragmentDirections.actionTodoListFragmentToTodoFragment(
                item
            )
            findNavController().navigate(action)
        } else {
            viewModel.onEvent(
                TodoListEvent.OnUpdateTodo(item)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onEvent(TodoListEvent.OnGetAllTodos)
        viewModel.viewEffect.observe(viewLifecycleOwner) {
            takeActionOn(it)
        }
        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoListAdapter
        }
        setListener()
    }

    private fun setListener() {
        binding.fabNewTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToTodoFragment()
            findNavController().navigate(action)
        }
    }

    private fun takeActionOn(todoState: TodoListState) {
        when (todoState) {
            is TodoListState.ShowAllTodos -> {
                val itemList = todoState.list
                if (itemList.isEmpty()) {
                    val action = TodoListFragmentDirections.actionTodoListFragmentToTodoFragment()
                    findNavController().navigate(action)
                } else {
                    loadRecyclerUi(itemList)
                }
            }
            is TodoListState.ShowUpdateTodo -> {
                val position = todoListAdapter.itemList.indexOfFirst {
                    it.id == todoState.todo.id
                }
                todoListAdapter.itemList[position] = todoState.todo
                todoListAdapter.notifyItemChanged(position)
            }
            is TodoListState.ShowGenericError -> {
                Toast.makeText(requireContext(), todoState.errorMessage, Toast.LENGTH_SHORT).show()
            }
            TodoListState.ClearState -> {}
        }
    }

    private fun loadRecyclerUi(itemList: List<Todo>) {
        todoListAdapter.itemList = itemList.toMutableList()
        todoListAdapter.notifyItemRangeInserted(todoListAdapter.itemList.size, itemList.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onEvent(
            TodoListEvent.OnClearState
        )
        mBinding = null
    }
}