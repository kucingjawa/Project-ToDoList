package dev.notsatria.todolist.screen.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.notsatria.todolist.data.local.entity.TaskEntity
import dev.notsatria.todolist.data.repository.TaskRepository
import dev.notsatria.todolist.model.Task
import dev.notsatria.todolist.utils.TaskFilterType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {

    val categories = TaskFilterType.entries

    private val _selectedCategory = MutableStateFlow(TaskFilterType.ALL)
    val selectedCategory = _selectedCategory.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasks = selectedCategory.flatMapLatest { filterType ->
        taskRepository.getAllTasks(filterType)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectCategory(category: TaskFilterType) {
        _selectedCategory.value = category
    }

    fun setTaskCompleted(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.setTaskCompleted(taskId, isCompleted)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task = task.toTaskEntity())
        }
    }
}