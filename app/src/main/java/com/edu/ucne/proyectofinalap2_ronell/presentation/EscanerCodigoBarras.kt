import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.edu.ucne.proyectofinalap2_ronell.CaptureActivityPortrait
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun EscanerCodigoBarras(
    onBarcodeScanned: (String) -> Unit
) {
    var resultadoEscaner by remember { mutableStateOf("") }

    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            resultadoEscaner = result.contents ?: "No hay un resultado"
            if (result.contents != null) {
                onBarcodeScanned(result.contents)
            }
        }
    )

    IconButton(
        onClick = {
            val scanOptions = ScanOptions()
            scanOptions.setBeepEnabled(true)
            scanOptions.setCaptureActivity(CaptureActivityPortrait::class.java)
            scanOptions.setOrientationLocked(false)
            scanLauncher.launch(scanOptions)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Build,
            contentDescription = "Escanear código de barras"
        )
    }

//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    var hasCameraPermission by remember {
//        mutableStateOf(
//            ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED
//        )
//    }
//
//    var launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = {granted ->
//            hasCameraPermission = granted
//        })
//
//    LaunchedEffect(key1 = true) {
//        launcher.launch(Manifest.permission.CAMERA)
//    }
//
//    if (hasCameraPermission) {
//        val barcodeView = remember {
//            CompoundBarcodeView(context).apply {
//                val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.EAN_13, BarcodeFormat.CODE_128)
//                barcodeView.decoderFactory = DefaultDecoderFactory(formats)
//                initializeFromIntent(android.content.Intent())
//                decodeContinuous(object : BarcodeCallback {
//                    override fun barcodeResult(result: BarcodeResult?) {
//                        if (result != null) {
//                            onBarcodeScanned(result.text)
//                        }
//                        onDismiss()
//                    }
//                })
//            }
//        }
//
//        DisposableEffect(lifecycleOwner) {
//            barcodeView.resume()
//            onDispose {
//                barcodeView.pause()
////                barcodeView.stopDecoding()
//            }
//        }
//
//        Box(modifier = Modifier.fillMaxSize()) {
//            AndroidView(factory = { barcodeView },
//                modifier = Modifier.fillMaxSize()
//            )
//            Button(onClick = onDismiss,
//                modifier = Modifier.padding(16.dp
//                )) {
//                Text(text = "Cancelar")
//
//            }
//        }
//    } else {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Text(text = "Se necesita el permiso de la camara para escanear codigos de barra.")
//        }
//    }
//
//
//    val barcodeView = remember {
//        DecoratedBarcodeView(context).apply {
//            initializeFromIntent(android.content.Intent())
//            decodeContinuous { result ->
//                onBarcodeScanned(result.text)
//                onDismiss()
//            }
//        }
//    }
//
//    DisposableEffect(lifecycleOwner) {
//        barcodeView.resume()
//        onDispose {
//            barcodeView.pause()
//        }
//    }
//
//    AndroidView({ barcodeView }, modifier = Modifier.fillMaxSize())
}