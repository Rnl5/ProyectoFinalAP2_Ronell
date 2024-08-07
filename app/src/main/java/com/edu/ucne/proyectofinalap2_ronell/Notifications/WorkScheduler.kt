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

//class NotificationWorker(
//    private val context: Context,
//    workerParams: WorkerParameters,
//    private val productoRepository: ProductoDao
//) : CoroutineWorker(context, workerParams) {
//
//    override suspend fun doWork(): Result {
//
////        val productoRepository = (context as InventarioApp).productoDao
//
//
//        val productos = productoRepository.getAllSync()
//
//        val expiringProductos = productos.filter {
//            val daysUntilExpiration = LocalDate.parse(it.fechaVencProducto).toEpochDay() - LocalDate.now().toEpochDay()
//            daysUntilExpiration in 0..5
//        }
//
//        val lowStockProductos = productos.filter {
//            it.stockProducto <= 5
//        }
//
//        if(expiringProductos.isNotEmpty() || lowStockProductos.isNotEmpty()){
//            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel(
//                    "PRODUCTO_NOTIFICATIONS",
//                    "Producto Notificaciones",
//                    NotificationManager.IMPORTANCE_DEFAULT
//                ).apply {
//                    description = "Notificaciones para el producto expirado o stock bajo"
//                }
//                notificationManager.createNotificationChannel(channel)
//            }
//            expiringProductos.forEach {
//                sendNotification("Alerta de producto Vencido", "El producto ${it.nombreProducto} se vencerá pronto!")
//            }
//
//            lowStockProductos.forEach {
//                sendNotification("Alerta de Stock bajo", "El producto ${it.nombreProducto} posee un stock bajo")
//            }
//        }
//        return Result.success()
//    }
//
//
//    private fun sendNotification(title: String, message: String) {
//        val notification = NotificationCompat.Builder(applicationContext, "PRODUCTO_NOTIFICACIONES")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        val notificationManager = NotificationManagerCompat.from(applicationContext)
//        if (ContextCompat.checkSelfPermission(
//                        applicationContext,
//                        Manifest.permission.POST_NOTIFICATIONS
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
//                }
//    }
//
//
//}

//@HiltWorker
//class NotificationWorker @Inject constructor(
//    @ApplicationContext context: Context,
//    workerParams: WorkerParameters,
//    private val productoRepository: ProductoDao,
//    ) : Worker(context, workerParams) {
//
//    override fun doWork(): Result {
//        val productos = productoRepository.getAll().first()
////        val notificationManager =
////            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val notificationManager = NotificationManagerCompat.from(applicationContext)
//
//        productos.forEach {
//            val daysUntilExpiration =
//                LocalDate.parse(it.fechaVencProducto).toEpochDay() - LocalDate.now().toEpochDay()
//
//            if (daysUntilExpiration <= 5 || it.stockProducto < 5) {
//                val notification =
//                    NotificationCompat.Builder(applicationContext, "PRODUCTO_NOTIFICACIONES")
//                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle("Producto por vencerse")
//                        .setContentText("El producto ${it.nombreProducto} vence en $daysUntilExpiration dias")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .build()
//
//                if (ContextCompat.checkSelfPermission(
//                        applicationContext,
//                        Manifest.permission.POST_NOTIFICATIONS
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
//                }
//            }
//
//            if (it.stockProducto <= 5) {
//                val notification =
//                    NotificationCompat.Builder(applicationContext, "PRODUCTO_NOTIFICACIONES")
//                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle("Stock bajo")
//                        .setContentText("El producto ${it.nombreProducto} tiene solo ${it.stockProducto} unidades")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .build()
//
//                if (ContextCompat.checkSelfPermission(
//                        applicationContext,
//                        Manifest.permission.POST_NOTIFICATIONS
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
//                }
//            }
//        }
//        return Result.success()
//    }
//}

//        val expiringProductos = productos.filter {
//            val daysUntilExpiration = LocalDate.parse(it.fechaVencProducto).toEpochDay() - LocalDate.now().toEpochDay()
//            daysUntilExpiration in 0..5
//        }
//
//        val lowStockProductos = productos.filter {
//            it.stockProducto <= 5
//        }
//
//        if(expiringProductos.isNotEmpty() || lowStockProductos.isNotEmpty()){
//            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel(
//                    "PRODUCTO_NOTIFICATIONS",
//                    "Producto Notificaciones",
//                    NotificationManager.IMPORTANCE_DEFAULT
//                ).apply {
//                    description = "Notificaciones para el producto expirado o stock bajo"
//                }
//                notificationManager.createNotificationChannel(channel)
//            }
//            expiringProductos.forEach {
//                sendNotification("Alerta de producto Vencido", "El producto ${it.nombreProducto} se vencerá pronto!")
//            }
//
//            lowStockProductos.forEach {
//                sendNotification("Alerta de Stock bajo", "El producto ${it.nombreProducto} posee un stock bajo")
//            }
//        }
//        return Result.success()
//    }
//
//
//    private fun sendNotification(title: String, message: String) {
//        val notification = NotificationCompat.Builder(applicationContext, "PRODUCTO_NOTIFICACIONES")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .build()
//
//        val notificationManager = NotificationManagerCompat.from(applicationContext)
//        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
//
//    }


//}

