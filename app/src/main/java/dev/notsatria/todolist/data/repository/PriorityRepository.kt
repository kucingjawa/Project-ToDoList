package dev.notsatria.todolist.data.repository

import dev.notsatria.todolist.data.local.dao.PriorityDao
import dev.notsatria.todolist.data.local.entity.PriorityEntity
import dev.notsatria.todolist.model.Priority

interface PriorityRepository {
    suspend fun getAllPriority(): List<Priority>
}

class PriorityRepositoryImpl(private val dao: PriorityDao) : PriorityRepository {
    override suspend fun getAllPriority(): List<Priority> {
        return dao.getAllPriorities().map { it.toPriority() }
    }
}