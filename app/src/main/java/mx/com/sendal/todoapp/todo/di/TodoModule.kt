package mx.com.sendal.todoapp.todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mx.com.sendal.data.datasources.TodoDataSource
import mx.com.sendal.data.repositories.TodoRepository
import mx.com.sendal.domain.usecases.CreateTodoUseCase
import mx.com.sendal.domain.usecases.DeleteTodoUseCase
import mx.com.sendal.domain.usecases.GetAllTodoUseCase
import mx.com.sendal.domain.usecases.UpdateTodoUseCase
import mx.com.sendal.todoapp.TodoDatabase
import mx.com.sendal.todoapp.todo.database.TodoDao
import mx.com.sendal.todoapp.todo.datasources.LocalTodoDataSource
import mx.com.sendal.todoapp.todo.datasources.RemoteTodoDataSource
import mx.com.sendal.todoapp.todo.utils.annotations.AnnotationLocalTodoDataSource
import mx.com.sendal.todoapp.todo.utils.annotations.AnnotationRemoteTodoDataSource

/**
 * @author Adán Castillo.
 */
@Module
@InstallIn(ViewModelComponent::class)
object TodoModule {

    @Provides
    @ViewModelScoped
    fun providesCreateTodoUseCase(todoRepository: TodoRepository): CreateTodoUseCase =
        CreateTodoUseCase(todoRepository)

    @Provides
    @ViewModelScoped
    fun providesUpdateTodoUseCase(todoRepository: TodoRepository): UpdateTodoUseCase =
        UpdateTodoUseCase(todoRepository)

    @Provides
    @ViewModelScoped
    fun providesDeleteTodoUseCase(todoRepository: TodoRepository): DeleteTodoUseCase =
        DeleteTodoUseCase(todoRepository)

    @Provides
    @ViewModelScoped
    fun providesGetAllTodoUseCase(todoRepository: TodoRepository): GetAllTodoUseCase =
        GetAllTodoUseCase(todoRepository)

    @Provides
    @ViewModelScoped
    @AnnotationRemoteTodoDataSource
    fun providesRemoteTodoDataSource(): TodoDataSource =
        RemoteTodoDataSource()

    @Provides
    @ViewModelScoped
    @AnnotationLocalTodoDataSource
    fun providesLocalTodoDataSource(todoDao: TodoDao): TodoDataSource =
        LocalTodoDataSource(todoDao)

    @Provides
    @ViewModelScoped
    fun providesTodoRepository(
        @AnnotationLocalTodoDataSource localTodoDataSource: TodoDataSource,
        @AnnotationRemoteTodoDataSource remoteTodoDataSource: TodoDataSource
    ): TodoRepository =
        TodoRepository(
            localTodoDataSource,
            remoteTodoDataSource
        )

    @Provides
    @ViewModelScoped
    fun providesTodoDao(todoDatabase: TodoDatabase): TodoDao =
        todoDatabase.todoDao()
}