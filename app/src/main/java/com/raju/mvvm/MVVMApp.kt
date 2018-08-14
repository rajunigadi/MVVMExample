package com.raju.mvvm

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDexApplication
import com.raju.mvvm.dagger.AppInjector
import com.raju.mvvm.dagger.components.DaggerAppComponent
import com.raju.mvvm.utilities.DebugLogTree
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MVVMApp: MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(DebugLogTree())
        }

        AppInjector.init(this)
    }

}