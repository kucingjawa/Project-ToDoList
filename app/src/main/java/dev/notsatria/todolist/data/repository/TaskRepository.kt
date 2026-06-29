package dev.notsatria.todolist.data.repository

import dev.notsatria.todolist.data.local.dao.TaskDao
import dev.notsatria.todolist.data.local.entity.TaskEntity
import dev.notsatria.todolist.data.local.relation.TaskAndPriority
import dev.notsatria.todolist.model.Task
import dev.notsatria.todolist.utils.FilterUtils
import dev.notsatria.todolist.utils.TaskFilterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

interface TaskRepository {
    fun getAllTasks(filterType: TaskFilterType): Flow<List<Task>>
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun getTaskById(id: Int): Task?
    suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean)
}

class TaskRepositoryImpl(private val dao: TaskDao) : TaskRepository {
    override fun getAllTasks(filterType: TaskFilterType): Flow<List<Task>> {
        val query = FilterUtils.getFilterType(filterType)
        val taskAndPriorityList = dao.getAllTasks(query)
        return taskAndPriorityList.map { it.map { it.toTask() } }
    }

    override suspend fun insertTask(task: TaskEntity) {
       return dao.insert(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        return dao.update(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        return dao.delete(task)
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)?.toTask()
    }

    override suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean) {
        return dao.setTaskCompleted(taskId, isCompleted)
    }
}