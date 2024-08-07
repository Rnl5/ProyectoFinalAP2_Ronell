package com.edu.ucne.proyectofinalap2_ronell

//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilCurvas
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilCapas
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilMosaico
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilNeomorfico
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilConBarraLateralScreen
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilConTarjetaScreen
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilGradienteFlotante
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilConFondoImagenScreen
//import com.edu.ucne.proyectofinalap2_ronell.presentation.perfil_usuario.PerfilMinimalistaScreen
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

//import com.google.android.gms.auth.api.identity.Identity

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val googleAuthUIClient by lazy {
//        InventoryAuthClient(
//            context = applicationContext,
//            oneTapClient = Identity.getSignInClient(applicationContext)
//        )
//    }

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


//                val navHost = rememberNavController()
//                NavHost(navController = navHost, startDestination = "sign_in") {
//                    composable("sign_in") {
//                        val viewModel = viewModel<LoginViewModel>()
//                        val state by viewModel.state.collectAsStateWithLifecycle()
//
//                        LaunchedEffect(key1 = Unit) {
//                            if (googleAuthUIClient.getSignedInUser() != null) {
//                                navHost.navigate("profile")
//                            }
//
//                        }
//
//                        val launcher = rememberLauncherForActivityResult(
//                            contract = ActivityResultContracts.StartIntentSenderForResult(),
//                            onResult = { result ->
//                                if (result.resultCode == RESULT_OK) {
//                                    lifecycleScope.launch {
//                                        val signInResult = googleAuthUIClient.signInWithIntent(
//                                            intent = result.data ?: return@launch
//                                        )
//                                        viewModel.onSignInResult(signInResult)
//                                    }
//                                }
//                            }
//                        )
//
//                        LaunchedEffect(key1 = state.isSignInSuccessful) {
//                            if (state.isSignInSuccessful) {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Sign in succesful",
//                                    Toast.LENGTH_LONG
//                                ).show()
//
//                                navHost.navigate("profile")
//                                viewModel.resetState()
//                            }
//
//                        }
//
//                        LoginScreen(
//                            state = state,
//                            onSignInClick = {
//                                lifecycleScope.launch {
//                                    val signInIntentSender = googleAuthUIClient.signIn()
//                                    launcher.launch(
//                                        IntentSenderRequest.Builder(
//                                            signInIntentSender ?: return@launch
//                                        ).build()
//                                    )
//                                }
//                            }
//                        )
//
//                    }
//                    composable("profile") {
//                        ProfileScreen(
//                            userData = googleAuthUIClient.getSignedInUser(),
//                            onSignOut = {
//                                lifecycleScope.launch {
//                                    googleAuthUIClient.signOut()
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Signet out",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//
//                                    navHost.popBackStack()
//                                }
//                            }
//                        )
//                    }
//                }


//                val navHost = rememberNavController()
//                InventarioNavHost(navHostController = navHost)
            }
        }

        NotificationUtils.createNotificationChannel(this)

        WorkScheduler.scheduleWork(this)
    }
}


//    private fun scheduleWork() {
//        val periodWorkRequest =
//            PeriodicWorkRequestBuilder<ProductoWorker>(1, TimeUnit.MINUTES)
//                .build()
//
//        WorkManager.getInstance(this)
//            .enqueueUniquePeriodicWork(
//                "PeriodicProductoWorker",
//                ExistingPeriodicWorkPolicy.REPLACE,
//                periodWorkRequest
//            )
//    }
//}
//        val initialDelay = calculateInitialDelay()
//
//        val firstWorkRequest = OneTimeWorkRequestBuilder<ProductoWorker>()
//            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
//            .build()

//        val periodWorkRequest =
//            PeriodicWorkRequestBuilder<ProductoWorker>(1, TimeUnit.MINUTES)
//                .build()
//
//        WorkManager.getInstance(this)
//            .enqueueUniquePeriodicWork(
//                "PeriodicProductoWorker",
//                ExistingPeriodicWorkPolicy.REPLACE,
//                periodWorkRequest
//            )
//            .apply {
//            enqueueUniqueWork(
//                "PrimerProductoWorker",
//                ExistingWorkPolicy.REPLACE,
//                firstWorkRequest
//            )

//    }
//}


//    private fun calculateInitialDelay() : Long {
//        val calendar = Calendar.getInstance()
//
//        val now = Calendar.getInstance()
//
//        calendar.set(Calendar.HOUR_OF_DAY, 6)
//        calendar.set(Calendar.MINUTE, 50)
//        calendar.set(Calendar.SECOND, 0)
//
//        if (calendar.before(now)) {
//            calendar.add(Calendar.DAY_OF_MONTH, 1)
//        }
//
//        return calendar.timeInMillis - now.timeInMillis
//    }
