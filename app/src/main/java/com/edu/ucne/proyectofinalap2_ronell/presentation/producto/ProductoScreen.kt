package com.edu.ucne.proyectofinalap2_ronell.presentation.producto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    LaunchedEffect(key1 = true) {
        viewModel.onSetProducto(productoId ?: 0)
    }

    ProductoBody(
        uiState = uiState,
        onSaveProducto = { viewModel.postProducto() },
        onDeleteProducto = { viewModel.deleteProducto() },
        irAProductoList = { irAProductoList() },
        onNombreChanged = viewModel::onNombreChanged,
        onDescripcionChanged = viewModel::onDescripcionChanged,
        onFechaVencProductoChanged = viewModel::onFechaVencProductoChanged,
        onPrecioChanged = viewModel::onPrecioChanged
    ) {

    }


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
) {
    var mostrarDatePicker by remember { mutableStateOf(false) }
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
                }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.nombre ?: "",
                        onValueChange = onNombreChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.nombreError != null,
                        label = { Text(text = "Nombre") }
                    )
                    uiState.nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                    OutlinedTextField(
                        value = uiState.descripcion ?: "",
                        onValueChange = onDescripcionChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.descripcionError != null,
                        label = { Text(text = "Descripción") }
                    )
                    if (uiState.descripcionError != null) {
                        Text(
                            text = uiState.descripcionError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.padding(2.dp))

                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { mostrarDatePicker = !mostrarDatePicker }
                    )
                    {
                        OutlinedTextField(
                            label = { Text(text = "Fecha de vencimiento") },
                            value = uiState.fechaVencProducto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            readOnly = true,
                            onValueChange = { onFechaVencProductoChanged },
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

                    Spacer(modifier = Modifier.padding(8.dp))

                    OutlinedTextField(
                        value = uiState.precio.toString(),
                        onValueChange = {valor ->
                            valor.trim().toIntOrNull()?.let { onPrecioChanged(it) }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.precioError != null,
                        label = { Text(text = "Precio") },
                    )
                    if (uiState.precioError != null) {
                        Text(
                            text = uiState.precioError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.padding(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onNewProducto) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Text(text = "Nuevo")
                        }

                        OutlinedButton(
                            onClick = {
                                val guardado = onSaveProducto()
                                if (guardado) {
                                    irAProductoList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Guardar"
                            )
                            Text(text = "Guardar")
                        }

                        OutlinedButton(onClick = {
                            if (uiState.productoId != null && uiState.productoId != 0) {
                                onDeleteProducto()
                                irAProductoList()
                            }
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar"
                            )
                            Text(text = "Borrar")
                        }
                    }

                }
            }

        }

    }


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
            onPrecioChanged = {}
        ) {

        }
    }
}