package com.raju.mvvm.dagger.modules.base

import android.app.Application
import android.content.Context
import com.raju.mvvm.MVVMApp
import dagger.Module
import dagger.Provides

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */
@Module
class AppModule {

    @Provides
    internal fun provideContext(application: MVVMApp): Context {
        return application.getApplicationContext()
    }

    @Provides
    internal fun provideApplication(application: MVVMApp): Application {
        return application
    }
}