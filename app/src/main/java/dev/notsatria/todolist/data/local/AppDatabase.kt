package dev.notsatria.todolist.data.local

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.notsatria.todolist.data.local.dao.PriorityDao
import dev.notsatria.todolist.data.local.dao.TaskDao
import dev.notsatria.todolist.data.local.entity.PriorityEntity
import dev.notsatria.todolist.data.local.entity.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@Database(entities = [TaskEntity::class, PriorityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun priorityDao(): PriorityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            Log.d("AppDatabase", "getInstance")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "todo_db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("AppDatabase", "onCreate")
                            CoroutineScope(Dispatchers.IO).launch {
                                prepopulatePriorities(INSTANCE!!)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        @OptIn(ExperimentalSerializationApi::class)
        private suspend fun prepopulatePriorities(db: AppDatabase) {
            try {
                val priorities = listOf(
                    PriorityEntity(id = 1, name = "Low", color = Color(0xFFF19292).toArgb()),
                    PriorityEntity(id = 2, name = "Medium", color = Color(0xFFF7D060).toArgb()),
                    PriorityEntity(id = 3, name = "High", color = Color(0xFFCBE2B0).toArgb())
                )

                db.priorityDao().insertAll(priorities)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}