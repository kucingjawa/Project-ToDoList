package dev.notsatria.todolist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.notsatria.todolist.data.local.dao.PriorityDao
import dev.notsatria.todolist.data.local.dao.TaskDao
import dev.notsatria.todolist.data.repository.PriorityRepository
import dev.notsatria.todolist.data.repository.PriorityRepositoryImpl
import dev.notsatria.todolist.data.repository.TaskRepository
import dev.notsatria.todolist.data.repository.TaskRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providePriorityRepository(dao: PriorityDao): PriorityRepository {
        return PriorityRepositoryImpl(dao)
    }

}