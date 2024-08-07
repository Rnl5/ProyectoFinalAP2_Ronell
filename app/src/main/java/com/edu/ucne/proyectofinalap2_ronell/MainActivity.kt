package com.edu.ucne.proyectofinalap2_ronell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.edu.ucne.proyectofinalap2_ronell.Notifications.NotificationUtils
import com.edu.ucne.proyectofinalap2_ronell.Notifications.WorkScheduler
import com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthViewModel
import com.edu.ucne.proyectofinalap2_ronell.presentation.navigation.InventarioNavHost
import com.edu.ucne.proyectofinalap2_ronell.ui.theme.ProyectoFinalAP2_RonellTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authViewModel : AuthViewModel by viewModels()

        setContent {
            ProyectoFinalAP2_RonellTheme {
                val navHost = rememberNavController()
                InventarioNavHost(navHostController = navHost,
                    authViewModel = authViewModel
                )
            }
        }
        NotificationUtils.createNotificationChannel(this)

        WorkScheduler.scheduleWork(this)
    }
}

