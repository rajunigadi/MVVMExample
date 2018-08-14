package com.raju.mvvm.dagger.modules.base

import android.app.Application
import android.arch.persistence.room.Room
import com.raju.mvvm.data.source.local.GitHubDatabase
import com.raju.mvvm.data.source.local.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */
@Module
class DatabaseModule {
    @Singleton
    @Provides
    internal fun providesGitHubDatabase(application: Application): GitHubDatabase {
        return Room
                .databaseBuilder(application, GitHubDatabase::class.java, "mvvm_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    internal fun providesUserDao(gitHubDatabase: GitHubDatabase): UserDao {
        return gitHubDatabase.userDao()
    }
}