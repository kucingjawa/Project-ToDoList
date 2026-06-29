package dev.notsatria.todolist.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import dev.notsatria.todolist.data.local.entity.PriorityEntity
import dev.notsatria.todolist.data.local.entity.TaskEntity
import dev.notsatria.todolist.data.local.relation.TaskAndPriority
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    @Transaction
    @RawQuery(observedEntities = [TaskEntity::class, PriorityEntity::class])
    fun getAllTasks(query: SupportSQLiteQuery): Flow<List<TaskAndPriority>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskAndPriority?

    @Delete
    suspend fun delete(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean)
}