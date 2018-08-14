package com.raju.mvvm.dagger.modules.base

import com.raju.mvvm.ui.activities.MainActivity
import com.raju.mvvm.dagger.PerActivity
import com.raju.mvvm.dagger.modules.UsersModule
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */
@Module(includes = arrayOf(AndroidInjectionModule::class))
abstract class ActivityBindingModule {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(UsersModule::class))
    abstract fun bindMainActivity(): MainActivity
}