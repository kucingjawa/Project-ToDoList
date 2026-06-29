package dev.notsatria.todolist.data.remote

import dev.notsatria.todolist.data.remote.dto.TaskDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("tasks")
    suspend fun getTasks(): List<TaskDto>

    @POST("tasks")
    suspend fun addTask(@Body task: TaskDto): TaskDto

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/" // Contoh URL API
    }
}
