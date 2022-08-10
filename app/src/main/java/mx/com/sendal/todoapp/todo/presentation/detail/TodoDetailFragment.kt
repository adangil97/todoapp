package mx.com.sendal.todoapp.todo.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mx.com.sendal.todoapp.R
import mx.com.sendal.todoapp.databinding.FragmentTodoDetailBinding
import mx.com.sendal.todoapp.todo.models.Todo
import mx.com.sendal.todoapp.todo.presentation.TodoActivity
import java.util.Calendar

@AndroidEntryPoint
class TodoDetailFragment : Fragment(R.layout.fragment_todo_detail) {
    private val viewModel: TodoDetailViewModel by viewModels()
    private var mBinding: FragmentTodoDetailBinding? = null
    private val binding get() = requireNotNull(mBinding)
    private val args: TodoDetailFragmentArgs by navArgs()
    private val todo = Todo(description = "", date = Calendar.getInstance().time.toString())
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewEffect.observe(viewLifecycleOwner) {
            takeActionOn(it)
        }
        args.todo?.let {
            loadUi(it)
        }
        setListener()
    }

    private fun setListener() {
        binding.clFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                binding.ivFavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gold
                    )
                )
            } else {
                binding.ivFavorite.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
        }
        binding.btnContinue.setOnClickListener {
            args.todo?.let { todo ->
                viewModel.onEvent(
                    TodoDetailEvent.OnUpdateTodo(
                        todo.copy(
                            description = binding.etDescriptionTodo.text.toString(),
                            isFavorite = isFavorite
                        )
                    )
                )
            } ?: run {
                viewModel.onEvent(
                    TodoDetailEvent.OnCreateTodo(
                        todo.copy(
                            description = binding.etDescriptionTodo.text.toString(),
                            isFavorite = isFavorite
                        )
                    )
                )
            }
        }
    }

    private fun takeActionOn(todoState: TodoDetailState) {
        when (todoState) {
            TodoDetailState.ShowCreateTodo -> {
                Toast.makeText(
                    requireContext(),
                    "Se ha creado la tarea",
                    Toast.LENGTH_SHORT
                ).show()
                reloadActivity()
            }
            TodoDetailState.ShowUpdateTodo -> {
                Toast.makeText(
                    requireContext(),
                    "Se ha actualizado la tarea",
                    Toast.LENGTH_SHORT
                ).show()
                reloadActivity()
            }
            TodoDetailState.ShowDescriptionEmpty -> {
                binding.etDescriptionTodo.error = getString(R.string.msg_description_required)
            }
            is TodoDetailState.ShowGenericError -> {
                Toast.makeText(requireContext(), todoState.errorMessage, Toast.LENGTH_SHORT).show()
            }
            TodoDetailState.ClearState -> {}
        }
    }

    private fun reloadActivity() {
        startActivity(
            Intent(requireContext(), TodoActivity::class.java)
        )
        requireActivity().finish()
    }

    private fun loadUi(todo: Todo) {
        binding.etDescriptionTodo.setText(todo.description)
        if (todo.isFavorite) {
            binding.ivFavorite.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gold
                )
            )
        }
        if (!todo.isComplete)
            showMenu(todo)
        else {
            binding.tvTodoComplete.visibility = View.VISIBLE
            binding.etDescriptionTodo.isEnabled = false
            binding.clFavorite.isEnabled = false
            binding.btnContinue.visibility = View.GONE
        }
    }

    private fun showMenu(todo: Todo) {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_todo, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.btn_delete -> {
                        val action =
                            TodoDetailFragmentDirections.actionTodoFragmentToDeleteTodoBottomSheet(
                                todo
                            )
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onEvent(
            TodoDetailEvent.OnClearState
        )
        mBinding = null
    }
}