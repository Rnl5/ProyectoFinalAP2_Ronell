package com.edu.ucne.proyectofinalap2_ronell

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.room.Room
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
//import com.edu.ucne.proyectofinalap2_ronell.Notifications.NotificationWorker
import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.ProductoDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.database.ProductoDb
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class InventarioApp : Application() {

    val productoDao: ProductoDao by lazy {
        val db = Room.databaseBuilder(
            this,
            ProductoDb::class.java,
            "Producto.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        db.productoDao()
    }
}