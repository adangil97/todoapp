package mx.com.sendal.todoapp

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.com.sendal.todoapp.todo.database.TodoDao
import mx.com.sendal.todoapp.todo.models.Todo

/**
 * @author Adán Castillo.
 */
@Database(
    entities = [
        Todo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}