package mx.com.sendal.todoapp

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Adán Castillo.
 */
@Module
@InstallIn(SingletonComponent::class)
object TodoGlobalModule {
    private const val DATABASE_NAME = "todo"

    @Provides
    @Singleton
    fun providesTodoDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
}