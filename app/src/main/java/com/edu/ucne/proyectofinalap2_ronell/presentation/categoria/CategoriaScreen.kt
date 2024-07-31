package com.edu.ucne.proyectofinalap2_ronell.presentation.categoria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.edu.ucne.proyectofinalap2_ronell.ui.theme.ProyectoFinalAP2_RonellTheme
@Composable
fun CategoriaScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    irACategoriaList: () -> Unit,
    categoriaId: Int?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.onSetCategoria(categoriaId ?: 0)
    }

    CategoriaBody(
        uiState = uiState,
        onSaveCategoria = { viewModel.postCategoria() },
        onDeleteCategoria = { viewModel.deleteCategoria() },
        irACategoriaList = { irACategoriaList() },
        onNombreCategoriaChanged = viewModel::onNombreCategoriaChanged,
        onDescripcionCategoriaChanged =viewModel::onDescripcionCategoriaChanged
    ) { }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriaBody(
    uiState: CategoriaUiState,
    onSaveCategoria: () -> Boolean,
    onDeleteCategoria: () -> Unit,
    irACategoriaList: () -> Unit,
    onNombreCategoriaChanged: (String) -> Unit,
    onDescripcionCategoriaChanged: (String) -> Unit,
    onNewCategoria: () -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro de Categorias") },
                navigationIcon = {
                    IconButton(onClick = irACategoriaList) {
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
                        value = uiState.nombreCategoria ?: "",
                        onValueChange = onNombreCategoriaChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.nombreCategoriaError != null,
                        label = { Text(text = "Nombre") }
                    )
                    uiState.nombreCategoriaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                    OutlinedTextField(
                        value = uiState.descripcionCategoria ?: "",
                        onValueChange = onDescripcionCategoriaChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.descripcionCategoriaError != null,
                        label = { Text(text = "Descripción") }
                    )
                    if (uiState.descripcionCategoriaError != null) {
                        Text(
                            text = uiState.descripcionCategoriaError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.padding(2.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onNewCategoria) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Text(text = "Nuevo")
                        }

                        OutlinedButton(
                            onClick = {
                                val guardado = onSaveCategoria()
                                if (guardado) {
                                    irACategoriaList()
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
                            if (uiState.categoriaId != null && uiState.categoriaId != 0) {
                                onDeleteCategoria()
                                irACategoriaList()
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

}

@Preview
@Composable
private fun ProductoPreview() {
    ProyectoFinalAP2_RonellTheme {
        CategoriaBody(
            uiState = CategoriaUiState(),
            onSaveCategoria = { true },
            onDeleteCategoria = {},
            irACategoriaList = {},
            onNombreCategoriaChanged = {},
            onDescripcionCategoriaChanged = {}
        ) {

        }
    }
}