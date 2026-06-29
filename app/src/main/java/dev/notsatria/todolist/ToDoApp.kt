package dev.notsatria.todolist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.notsatria.todolist.navigation.Screen
import dev.notsatria.todolist.screen.add_todo.AddTodoRoute
import dev.notsatria.todolist.screen.edit_todo.EditToDoRoute
import dev.notsatria.todolist.screen.todo_list.TodoListRoute

@Composable
fun ToDoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.ToDoList) {
        composable<Screen.ToDoList> {
            TodoListRoute(
                modifier = modifier,
                navigateToAddTask = {
                    navController.navigate(Screen.AddTodo)
                }, navigateToEditTask = { taskId ->
                    navController.navigate(Screen.EditTodo(taskId = taskId))
                })
        }
        composable<Screen.AddTodo> {
            AddTodoRoute(modifier = modifier, navigateBack = {
                navController.navigateUp()
            })
        }
        composable<Screen.EditTodo> {
            EditToDoRoute(modifier = modifier, navigateBack = {
                navController.navigateUp()
            })
        }
    }
}