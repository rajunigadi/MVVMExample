package com.raju.mvvm.utilities

import android.os.Build
import android.util.Log
import timber.log.Timber

/**
 * Created by Rajashekhar Vanahalli on 09/07/18.
 */
class DebugLogTree: Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String?, thowable: Throwable?) {
        var prior = priority
        if(Build.MANUFACTURER.equals("HUAWEI") || Build.MANUFACTURER.equals("samsung")) {
            if(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                prior = Log.ERROR;
            }
        }
        super.log(prior, tag, message, thowable)
    }

    override fun createStackElementTag(element: StackTraceElement?): String {
        if(element == null) return super.createStackElementTag(element) else return super.createStackElementTag(element) + " - " + element.lineNumber
    }
}