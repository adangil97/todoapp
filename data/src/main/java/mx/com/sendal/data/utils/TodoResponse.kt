package mx.com.sendal.data.utils

/**
 * @author Adán Castillo.
 */
sealed class TodoResponse<T> {

    data class Success<T>(
        val content: T
    ) : TodoResponse<T>()

    data class Failed<T>(
        val errorMessage: String,
        val errorCode: Int? = null,
        val content: T? = null
    ) : TodoResponse<T>()
}
