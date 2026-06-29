package dev.notsatria.todolist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.notsatria.todolist.data.local.entity.PriorityEntity

@Dao
interface PriorityDao {
    @Query("SELECT * FROM priority")
    suspend fun getAllPriorities(): List<PriorityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<PriorityEntity>)
}