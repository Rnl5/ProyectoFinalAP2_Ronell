package com.edu.ucne.proyectofinalap2_ronell.presentation.producto

import EscanerCodigoBarras
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import com.edu.ucne.proyectofinalap2_ronell.presentation.components.Combobox
import com.edu.ucne.proyectofinalap2_ronell.ui.theme.ProyectoFinalAP2_RonellTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun ProductoScreen(
    viewModel: ProductoViewModel = hiltViewModel(),
    irAProductoList: () -> Unit,
    productoId: Int?,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    val categorias by viewModel.categoria.collectAsStateWithLifecycle()

    val marcas by viewModel.marca.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = true) {
        viewModel.onSetProducto(productoId ?: 0)
        viewModel.getProductosLocal()
    }

    ProductoBody(
        uiState = uiState,
        onSaveProducto = { viewModel.postProducto() },
        onDeleteProducto = { viewModel.deleteProducto() },
        irAProductoList = { irAProductoList() },
        onNombreChanged = viewModel::onNombreChanged,
        onDescripcionChanged = viewModel::onDescripcionChanged,
        onFechaVencProductoChanged = viewModel::onFechaVencProductoChanged,
        onPrecioChanged = viewModel::onStockChanged,
        onCategoriaChanged = viewModel::onCategoriaChanged,
        onMarcaChanged = viewModel::onMarcaChanged,
        onNewProducto = viewModel::newProducto,
        categorias = categorias,
        marcas = marcas,
        onBarcodeScanned = viewModel::onBarcodeScanned,
        onPrecioCompraChanged = viewModel::onPrecioCompraChanged,
        onPrecioVentaChanged = viewModel::onPrecioVentaChanged,
    )



}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductoBody(
    uiState: ProductoUiState,
    onSaveProducto: () -> Boolean,
    onDeleteProducto: () -> Unit,
    irAProductoList: () -> Unit,
    onNombreChanged: (String) -> Unit,
    onDescripcionChanged: (String) -> Unit,
    onFechaVencProductoChanged: (String) -> Unit,
    onPrecioChanged: (Int) -> Unit,
    onNewProducto: () -> Unit,
    categorias: List<CategoriaEntity>,
    marcas: List<MarcaEntity>,
    onCategoriaChanged: (Int) -> Unit,
    onMarcaChanged: (Int) -> Unit,
    onBarcodeScanned: (String) -> Unit,
    onPrecioCompraChanged: (Double) -> Unit,
    onPrecioVentaChanged: (Double) -> Unit,
) {


    var mostrarToast by remember { mutableStateOf(false) }

    var mostrarDatePicker by remember { mutableStateOf(false) }

    var tabIndex by remember { mutableStateOf(0) }
    val unDia = 86400000

    val state = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= System.currentTimeMillis() - unDia
        }
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro de productos") },
                navigationIcon = {
                    IconButton(onClick = irAProductoList) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atras"
                        )
                    }
                },
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TabRow(selectedTabIndex = tabIndex) {
                        Tab(selected = tabIndex == 0, onClick = { tabIndex = 0 }) {
                            Text(
                                text = "Informacion Basica",
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Tab(selected = tabIndex == 1, onClick = { tabIndex = 1 }) {
                            Text(text = "Detalles", modifier = Modifier.padding(vertical = 8.dp))
                        }

                        Tab(selected = tabIndex == 2, onClick = { tabIndex = 2 }) {
                            Text(text = "Precios", modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }


                    when (tabIndex) {
                        0 -> InformacionBasicaTab(
                            uiState = uiState,
                            onNombreChanged = onNombreChanged,
                            onDescripcionChanged = onDescripcionChanged,
                            onFechaVencProductoChanged = onFechaVencProductoChanged,
                            onMostrarDatePickerChanged = { mostrarDatePicker = it },
                            state = state,
                            onPrecioChanged = onPrecioChanged
                        )


                        1 -> DetallesTab(
                            uiState = uiState,
                            categorias = categorias,
                            marcas = marcas,
                            onCategoriaChanged = onCategoriaChanged,
                            onMarcaChanged = onMarcaChanged,
                            onBarcodeScanned = onBarcodeScanned
                        )


                        2 -> PreciosTab(
                            uiState = uiState,
                            onPrecioCompraChanged = onPrecioCompraChanged,
                            onPrecioVentaChanged = onPrecioVentaChanged,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onNewProducto,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Nuevo")
                        }

                        Button(onClick = {
                            if (onSaveProducto()) irAProductoList()
                            mostrarToast = true
                        }
                        ) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Guardar")
                        }

                        Button(
                            onClick = {
                                if (uiState.productoId != null && uiState.productoId != 0) {
                                    onDeleteProducto()
                                    irAProductoList()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Spacer(Modifier.width(4.dp))
                            Text("Borrar")
                        }
                    }
                }
            }
        }

        if(mostrarToast) {
            Notification("${uiState.nombre} ha sido añadido correctamente")
            mostrarToast = false
        }


    }
}


@Preview
@Composable
private fun ProductoPreview() {
    ProyectoFinalAP2_RonellTheme {
        ProductoBody(
            uiState = ProductoUiState(),
            onSaveProducto = { true },
            onDeleteProducto = {},
            irAProductoList = {},
            onNombreChanged = {},
            onDescripcionChanged = {},
            onFechaVencProductoChanged = {},
            onPrecioChanged = {},
            onNewProducto = {},
            categorias = emptyList(),
            marcas = emptyList(),
            onCategoriaChanged = {},
            onMarcaChanged = {},
            onBarcodeScanned = {},
            onPrecioCompraChanged = {},
            onPrecioVentaChanged = {},
        )
    }
}

@Composable
fun Notification(message: String) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionBasicaTab(
    uiState: ProductoUiState,
    onNombreChanged: (String) -> Unit,
    onDescripcionChanged: (String) -> Unit,
    onFechaVencProductoChanged: (String) -> Unit,
    onMostrarDatePickerChanged: (Boolean) -> Unit,
    state: DatePickerState,
    onPrecioChanged: (Int) -> Unit,
) {

    var mostrarDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = uiState.nombre ?: "",
        onValueChange = onNombreChanged,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Nombre") },
        isError = uiState.nombreError != null
    )
    uiState.nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = uiState.descripcion ?: "",
        onValueChange = onDescripcionChanged,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Descripción") },
        isError = uiState.descripcionError != null
    )
    uiState.descripcionError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

    Spacer(modifier = Modifier.height(8.dp))

    ExposedDropdownMenuBox(
        expanded = false,
        onExpandedChange = { mostrarDatePicker = !mostrarDatePicker }
    )
    {
        OutlinedTextField(
            label = { Text(text = "Fecha de vencimiento") },
            value = uiState.fechaVencProducto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            readOnly = true,
            onValueChange = onFechaVencProductoChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                IconButton(
                    onClick = {
                        mostrarDatePicker = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date Picker"
                    )
                }
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .clickable(enabled = true) {
                    mostrarDatePicker = true
                }
        )
        uiState.fechaVencProductoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }

    uiState.fechaVencProductoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = uiState.stock.toString(),
        onValueChange = { valor ->
            valor.trim().toIntOrNull()?.let { onPrecioChanged(it) }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Stock") },
        isError = uiState.stockError != null
    )
    uiState.stockError?.let { Text(it, color = MaterialTheme.colorScheme.error) }


    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        onFechaVencProductoChanged(
                            state.selectedDateMillis?.let {
                                Instant.ofEpochMilli(it).atZone(
                                    ZoneId.of("UTC")
                                ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            }.toString()
                        )
                        mostrarDatePicker = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarDatePicker = false }) {
                    Text(text = "Cancelar")
                }
            },
        )
        {
            DatePicker(state)
        }
    }

}

@Composable
fun DetallesTab(
    uiState: ProductoUiState,
    categorias: List<CategoriaEntity>,
    marcas: List<MarcaEntity>,
    onCategoriaChanged: (Int) -> Unit,
    onMarcaChanged: (Int) -> Unit,
    onBarcodeScanned: (String) -> Unit,
) {
    var categoriaSeleccionada2 by remember { mutableStateOf<CategoriaEntity?>(null) }
    var marcaSeleccionada by remember { mutableStateOf<MarcaEntity?>(null) }

    Combobox(
        label = "Categorías",
        items = categorias,
        selectedItem = categoriaSeleccionada2,
        selectedItemString = { it?.nombreCategoria ?: "" },
        onItemSelected = {
            onCategoriaChanged(it?.categoriaId ?: 0)
            categoriaSeleccionada2 = it
            uiState.categoriaId = it?.categoriaId
        },
        itemTemplate = { Text(it.nombreCategoria ?: "") },
        isErrored = false
    )

    Spacer(modifier = Modifier.height(8.dp))

    Combobox(
        label = "Marcas",
        items = marcas,
        selectedItem = marcaSeleccionada,
        selectedItemString = { it?.nombreMarca ?: "" },
        onItemSelected = {
            onMarcaChanged(it?.marcaId ?: 0)
            marcaSeleccionada = it
            uiState.marcaId = it?.marcaId
        },
        itemTemplate = { Text(it.nombreMarca ?: "") },
        isErrored = false
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = uiState.codigoBarras ?: "",
        onValueChange = onBarcodeScanned,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Código de barras") },
        isError = uiState.codigoBarrasError != null,
        trailingIcon = {EscanerCodigoBarras (
            onBarcodeScanned = onBarcodeScanned
        )
        }
    )
}

@Composable
fun PreciosTab(
    uiState: ProductoUiState,
    onPrecioCompraChanged: (Double) -> Unit,
    onPrecioVentaChanged: (Double) -> Unit,
) {

    OutlinedTextField(
        value = uiState.precioCompra.toString(),
        onValueChange = { valor ->
            valor.trim().toDoubleOrNull()?.let { onPrecioCompraChanged(it) }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Precio de Compra") },
        isError = uiState.precioCompraError != null
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = uiState.precioVenta.toString(),
        onValueChange = { valor ->
            valor.trim().toDoubleOrNull()?.let { onPrecioVentaChanged(it) }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Precio de Venta") },
        isError = uiState.precioVentaError != null
    )
    uiState.precioVentaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
}

