package com.edu.ucne.proyectofinalap2_ronell.Notifications

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.edu.ucne.proyectofinalap2_ronell.InventarioApp
import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.ProductoDao
import com.edu.ucne.proyectofinalap2_ronell.data.repository.ProductoRepositoryImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ProductoWorker(
     private val appContext: Context,
    workerParams: WorkerParameters,
) : Worker(appContext, workerParams) {

    private val productoDao = (appContext as InventarioApp).productoDao

    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            checkFechaVencimientoProductos()
            checkStockBajoProductos()
        }
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d("ProductoWorker", "Work cancelled")
    }

    private suspend fun checkFechaVencimientoProductos() {
        val productos = productoDao.getAllSync()
        val fechaActual = LocalDate.now()
        var notificationid = 1
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        productos.forEach { producto ->
            val fechaExpiracion = LocalDate.parse(producto.fechaVencProducto, formatter)
            if(fechaExpiracion.isAfter(fechaActual) && fechaExpiracion.isBefore(fechaActual.plusDays(6))) {
                NotificationUtils.sendNotification(
                    appContext,
                    "Hay un producto proximo a vencer",
                    "El producto ${producto.nombreProducto} vence en ${fechaExpiracion.minusDays(fechaActual.toEpochDay()).dayOfMonth} dias",
                    notificationid++
                )
            }

        }
    }

    private suspend fun checkStockBajoProductos() {
        val productos = productoDao.getAllSync()
        var notificationid = 1


        productos.forEach { producto ->
            if(producto.stockProducto <= 5) {
                NotificationUtils.sendNotification(
                    appContext,
                    "Stock bajo",
                    "El producto ${producto.nombreProducto} tiene un stock de ${producto.stockProducto}",
                    notificationid++
                )
            }
        }
    }
}