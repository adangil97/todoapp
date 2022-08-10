package mx.com.sendal.todoapp.todo.presentation.delete

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import mx.com.sendal.todoapp.R
import mx.com.sendal.todoapp.databinding.BottomSheetTodoDeleteBinding
import mx.com.sendal.todoapp.todo.presentation.TodoActivity

@AndroidEntryPoint
class TodoDeleteBottomSheet : BottomSheetDialogFragment() {
    private val viewModel: TodoDeleteViewModel by viewModels()
    private var mBinding: BottomSheetTodoDeleteBinding? = null
    private val binding get() = requireNotNull(mBinding)
    private val args: TodoDeleteBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = BottomSheetTodoDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewEffect.observe(viewLifecycleOwner) {
            takeActionOn(it)
        }
        binding.tvDescription.text = getString(
            R.string.msg_content_delete_todo,
            args.todo.description
        )
        setListeners()
    }

    private fun takeActionOn(todoDeleteState: TodoDeleteState) {
        when (todoDeleteState) {
            TodoDeleteState.ShowDeleteTodoDelete -> {
                startActivity(
                    Intent(requireContext(), TodoActivity::class.java)
                )
                requireActivity().finish()
            }
            is TodoDeleteState.ShowGenericError -> {
                Toast.makeText(requireContext(), todoDeleteState.errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
            TodoDeleteState.ClearState -> {}
        }
    }

    private fun setListeners() {
        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvContinue.setOnClickListener {
            viewModel.onEvent(
                TodoDeleteEvent.OnDeleteTodoDelete(args.todo)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onEvent(
            TodoDeleteEvent.OnClearState
        )
        mBinding = null
    }
}