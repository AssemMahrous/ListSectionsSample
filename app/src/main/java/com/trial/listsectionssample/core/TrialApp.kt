package com.trial.listsectionssample.core

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.trial.listsectionssample.BuildConfig.DEBUG
import com.trial.listsectionssample.core.di.AppComponent
import com.trial.listsectionssample.core.di.AppInjector
import com.trial.listsectionssample.core.di.DaggerAppComponent
import com.trial.listsectionssample.core.utils.ReleaseTree
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class TrialApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        context = this
        AppInjector.init(this)
        if (DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter())
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }
        component = createDaggerComponent()
        Fresco.initialize(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        private lateinit var context: TrialApp
        private var component: AppComponent? = null

        fun clearComponent() {
            component = null
        }

        fun createDaggerComponent(): AppComponent {
            return DaggerAppComponent.builder()
                .application(context)
                .context(context.applicationContext)
                .build()
        }

        val gson = Gson()
    }
}