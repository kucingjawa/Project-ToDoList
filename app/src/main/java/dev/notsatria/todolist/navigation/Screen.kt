package dev.notsatria.todolist.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object ToDoList : Screen()

    @Serializable
    data object AddTodo : Screen()

    @Serializable
    data class EditTodo(val taskId: Int = -1) : Screen()
}