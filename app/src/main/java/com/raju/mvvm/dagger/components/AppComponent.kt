package com.raju.mvvm.dagger.components

import com.raju.mvvm.MVVMApp
import com.raju.mvvm.dagger.modules.base.ActivityBindingModule
import com.raju.mvvm.dagger.modules.base.AppModule
import com.raju.mvvm.dagger.modules.base.DatabaseModule
import com.raju.mvvm.dagger.modules.base.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ActivityBindingModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: MVVMApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MVVMApp)
}