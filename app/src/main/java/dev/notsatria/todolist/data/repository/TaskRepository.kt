package dev.notsatria.todolist.data.repository

import dev.notsatria.todolist.data.local.dao.TaskDao
import dev.notsatria.todolist.data.local.entity.TaskEntity
import dev.notsatria.todolist.data.remote.ApiService
import dev.notsatria.todolist.data.remote.dto.TaskDto
import dev.notsatria.todolist.model.Task
import dev.notsatria.todolist.utils.FilterUtils
import dev.notsatria.todolist.utils.TaskFilterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TaskRepository {
    fun getAllTasks(filterType: TaskFilterType): Flow<List<Task>>
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun getTaskById(id: Int): Task?
    suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean)
    suspend fun syncTasksFromServer()
}

class TaskRepositoryImpl(
    private val dao: TaskDao,
    private val apiService: ApiService
) : TaskRepository {
    override fun getAllTasks(filterType: TaskFilterType): Flow<List<Task>> {
        val query = FilterUtils.getFilterType(filterType)
        val taskAndPriorityList = dao.getAllTasks(query)
        return taskAndPriorityList.map { it.map { it.toTask() } }
    }

    override suspend fun insertTask(task: TaskEntity) {
        dao.insert(task)
        try {
            apiService.addTask(
                TaskDto(
                    id = task.id,
                    title = task.title,
                    description = task.description,
                    isCompleted = task.isCompleted,
                    priorityId = task.priorityId,
                    createdAt = task.createdAt
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateTask(task: TaskEntity) {
        dao.update(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        dao.delete(task)
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)?.toTask()
    }

    override suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean) {
        dao.setTaskCompleted(taskId, isCompleted)
    }

    override suspend fun syncTasksFromServer() {
        try {
            val remoteTasks = apiService.getTasks()
            val entities = remoteTasks.map { dto ->
                TaskEntity(
                    id = dto.id ?: 0,
                    title = dto.title,
                    description = dto.description,
                    isCompleted = dto.isCompleted,
                    priorityId = dto.priorityId,
                    createdAt = dto.createdAt
                )
            }
            entities.forEach { dao.insert(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
