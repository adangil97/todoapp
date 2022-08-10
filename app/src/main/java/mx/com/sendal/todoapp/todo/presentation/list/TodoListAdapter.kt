package mx.com.sendal.todoapp.todo.presentation.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mx.com.sendal.todoapp.R
import mx.com.sendal.todoapp.databinding.ItemTodoBinding
import mx.com.sendal.todoapp.todo.models.Todo
import mx.com.sendal.todoapp.todo.presentation.list.TodoListAdapter.TodoListViewHolder

/**
 * @author Adán Castillo.
 */
class TodoListAdapter(
    var itemList: MutableList<Todo>,
    private val listener: (Todo, Boolean) -> Unit
) : RecyclerView.Adapter<TodoListViewHolder>() {

    inner class TodoListViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    listener(item, true)
                }
            }
            binding.ivEditTodo.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    listener(item, true)
                }
            }
            binding.rbComplete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    listener(item.copy(isComplete = !item.isComplete), false)
                }
            }
            binding.ivFavorite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    listener(item.copy(isFavorite = !item.isFavorite), false)
                }
            }
        }

        fun bind(item: Todo) {
            binding.rbComplete.isChecked = item.isComplete
            if (item.isComplete) {
                binding.tvDescription.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                binding.tvDate.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
            if (item.isFavorite) {
                binding.ivFavorite.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gold
                    )
                )
            } else {
                binding.ivFavorite.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
            }
            binding.tvDescription.text = item.description
            binding.tvDate.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder =
        TodoListViewHolder(
            ItemTodoBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_todo,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size
}