package dev.notsatria.todolist.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object FilterUtils {

    fun getFilterType(filterType: TaskFilterType): SimpleSQLiteQuery {
        val query = StringBuilder().append("SELECT * FROM tasks ")
        when (filterType) {
            TaskFilterType.LOW -> query.append("WHERE priorityId = 1")
            TaskFilterType.MEDIUM -> query.append("WHERE priorityId = 2")
            TaskFilterType.HIGH -> query.append("WHERE priorityId = 3")
            TaskFilterType.COMPLETED -> query.append("WHERE isCompleted = true")
            TaskFilterType.UNCOMPLETED -> query.append("WHERE isCompleted = false")
            else -> query.append("")
        }
        return SimpleSQLiteQuery(query.append(" ORDER BY createdAt DESC").toString())
    }
}

enum class TaskFilterType(val label: String) {
    ALL("All"),
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    COMPLETED("Completed"),
    UNCOMPLETED("Uncompleted")
}