package mx.com.sendal.data.models

/**
 * @author Adán Castillo.
 */
data class TodoModel(
    val id: Int,
    val isComplete: Boolean,
    val isFavorite: Boolean,
    val description: String,
    val date: String
)
