package com.edu.ucne.proyectofinalap2_ronell.di


import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
//import com.edu.ucne.proyectofinalap2_ronell.Notifications.NotificationWorker
//import com.edu.ucne.proyectofinalap2_ronell.Notifications.NotificationWorkerFactoryWrapper
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

//
//@MapKey
//@Retention(AnnotationRetention.RUNTIME)
//annotation class WorkerKey(val value: KClass<out ListenableWorker>)
//
//interface ChildWorkerFactory {
//    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
//}
//
//@InstallIn(SingletonComponent::class)
//@Module
//interface WorkerModule {
//    @Binds
//    @IntoMap
//    @WorkerKey(NotificationWorker::class)
//    fun bindWorkerFactory(factory: NotificationWorkerFactoryWrapper): ChildWorkerFactory
//}