package mx.com.sendal.todoapp.todo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @author Adán Castillo.
 */
@Entity
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isComplete: Boolean = false,
    val isFavorite: Boolean = false,
    val description: String,
    val date: String
) : Parcelable