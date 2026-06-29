package dev.notsatria.todolist.screen.edit_todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.notsatria.todolist.screen.components.ToDoContent
import dev.notsatria.todolist.screen.components.ToDoScreenType
import dev.notsatria.todolist.theme.ToDoListTheme
import androidx.compose.runtime.getValue
import dev.notsatria.todolist.model.Priority
import dev.notsatria.todolist.model.Task

@Composable
fun EditToDoRoute(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: EditTodoViewModel = hiltViewModel()
) {
    val task by viewModel.task.collectAsStateWithLifecycle()
    val priorities by viewModel.priorities.collectAsStateWithLifecycle()
    val selectedPriority by viewModel.selectedPriority.collectAsStateWithLifecycle()

    var (title, setTitle) = remember(task) { mutableStateOf(task?.title ?: "") }
    var (desc, setDesc) = remember(task) { mutableStateOf(task?.description ?: "") }

    EditToDoScreen(
        modifier = modifier,
        title = title,
        setTitle = setTitle,
        desc = desc,
        setDesc = setDesc,
        navigateBack = navigateBack,
        onEditTask = {
            viewModel.updateTask(title = title, desc = desc)
            navigateBack()
        },
        selectedPriority = selectedPriority,
        priorities = priorities,
        onSelectPriority = { priority ->
            viewModel.selectPriority(priority)
        }
    )
}

@Composable
fun EditToDoScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    setTitle: (String) -> Unit = {},
    desc: String = "",
    setDesc: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    onEditTask: () -> Unit = {},
    selectedPriority: Priority? = null,
    priorities: List<Priority> = emptyList(),
    onSelectPriority: (Priority) -> Unit = {}
) {
    ToDoContent(
        modifier = modifier,
        title = title,
        setTitle = setTitle,
        desc = desc,
        setDesc = setDesc,
        navigateBack = navigateBack,
        onButtonClick = onEditTask,
        type = ToDoScreenType.EDIT,
        selectedPriority = selectedPriority,
        priorities = priorities,
        onSelectPriority = onSelectPriority
    )
}

@Preview
@Composable
fun EditToDoScreenPreview(modifier: Modifier = Modifier) {
    ToDoListTheme {
        EditToDoScreen()
    }
}