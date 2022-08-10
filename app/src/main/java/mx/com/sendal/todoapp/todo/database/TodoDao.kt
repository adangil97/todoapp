package mx.com.sendal.todoapp.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.com.sendal.todoapp.todo.models.Todo

/**
 * @author Adán Castillo.
 */
@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}