package dev.notsatria.todolist.screen.add_todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.notsatria.todolist.screen.components.ToDoContent
import dev.notsatria.todolist.screen.components.ToDoScreenType
import dev.notsatria.todolist.theme.ToDoListTheme

@Composable
fun AddTodoRoute(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: AddTodoViewModel = hiltViewModel()
) {
    var (title, setTitle) = remember { mutableStateOf("") }
    var (desc, setDesc) = remember { mutableStateOf("") }
    val priorities by viewModel.priorities.collectAsStateWithLifecycle()
    val selectedPriority by viewModel.selectedPriority.collectAsStateWithLifecycle()

    ToDoContent(
        modifier = modifier,
        title = title,
        setTitle = setTitle,
        desc = desc,
        setDesc = setDesc,
        navigateBack = navigateBack,
        onButtonClick = {
            viewModel.insertTask(
                title = title,
                desc = desc
            )
            navigateBack()
        },
        type = ToDoScreenType.ADD,
        selectedPriority = selectedPriority,
        priorities = priorities,
        onSelectPriority = {
            viewModel.selectPriority(it)
        }
    )
}

@Preview
@Composable
fun AddTodoScreenPreview(modifier: Modifier = Modifier) {
    ToDoListTheme {
        ToDoContent(type = ToDoScreenType.ADD)
    }
}