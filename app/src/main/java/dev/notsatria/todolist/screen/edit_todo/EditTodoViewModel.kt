package dev.notsatria.todolist.screen.edit_todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.notsatria.todolist.data.local.entity.TaskEntity
import dev.notsatria.todolist.data.repository.PriorityRepository
import dev.notsatria.todolist.data.repository.TaskRepository
import dev.notsatria.todolist.model.Priority
import dev.notsatria.todolist.model.Task
import dev.notsatria.todolist.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val priorityRepository: PriorityRepository
) : ViewModel() {

    val editTodo: Screen.EditTodo = savedStateHandle.toRoute<Screen.EditTodo>()

    private val _task = MutableStateFlow<Task?>(null)
    val task = _task.asStateFlow()

    private val _priorities = MutableStateFlow<List<Priority>>(emptyList())
    val priorities = _priorities.asStateFlow()

    private val _selectedPriority = MutableStateFlow<Priority?>(null)
    val selectedPriority = _selectedPriority.asStateFlow()

    init {
        getTask()
        getPriorities()
    }

    private fun getTask() {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(editTodo.taskId)
            _task.value = task
            selectPriority(task?.priority)
        }
    }

    private fun getPriorities() {
        viewModelScope.launch {
            _priorities.value = withContext(Dispatchers.IO) { priorityRepository.getAllPriority() }
        }
    }

    fun selectPriority(priority: Priority?) {
        _selectedPriority.value = priority
    }

    fun updateTask(title: String, desc: String) {
        viewModelScope.launch {
            val taskEntity = task.value?.copy(
                title = title,
                description = desc,
                priorityId = selectedPriority.value?.id ?: -1,
            )?.toTaskEntity()

            taskEntity?.let {
                withContext(Dispatchers.IO) {
                    taskRepository.updateTask(it)
                }
            }
        }
    }
}