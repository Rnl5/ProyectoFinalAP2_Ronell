package com.edu.ucne.proyectofinalap2_ronell.Notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun scheduleWork(context: Context) {
        val periodWorkRequest =
            PeriodicWorkRequestBuilder<ProductoWorker>(1, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "PeriodicProductoWorker",
                ExistingPeriodicWorkPolicy.REPLACE,
                periodWorkRequest
            )
    }
}