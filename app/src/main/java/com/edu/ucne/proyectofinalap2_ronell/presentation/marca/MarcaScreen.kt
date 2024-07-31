package com.edu.ucne.proyectofinalap2_ronell.presentation.marca

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
fun MarcaScreen(
    viewModel: MarcaViewModel = hiltViewModel(),
    irAMarcaList: () -> Unit,
    marcaId: Int?,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.onSetMarca(marcaId ?: 0)
    }

    MarcaBody(
        uiState = uiState,
        onSaveMarca = { viewModel.postMarca() },
        onDeleteMarca = { viewModel.deleteMarca() },
        irAMarcaList = { irAMarcaList() },
        onNombreMarcaChanged = viewModel::onNombreMarcaChanged
    ) {

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MarcaBody(
    uiState: MarcaUiState,
    onSaveMarca: () -> Boolean,
    onDeleteMarca: () -> Unit,
    irAMarcaList: () -> Unit,
    onNombreMarcaChanged: (String) -> Unit,
    onNewMarca: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro de Marcas") },
                navigationIcon = {
                    IconButton(onClick = irAMarcaList) {
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
                        value = uiState.nombreMarca ?: "",
                        onValueChange = onNombreMarcaChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.nombreMarcaError != null,
                        label = { Text(text = "Nombre") }
                    )
                    uiState.nombreMarcaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }


                    Spacer(modifier = Modifier.padding(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onNewMarca) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Text(text = "Nuevo")
                        }

                        OutlinedButton(
                            onClick = {
                                val guardado = onSaveMarca()
                                if (guardado) {
                                    irAMarcaList()
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
                            if (uiState.marcaId != null && uiState.marcaId != 0) {
                                onDeleteMarca()
                                irAMarcaList()
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
private fun MarcaPreview() {
    ProyectoFinalAP2_RonellTheme {
        MarcaBody(
            uiState = MarcaUiState(),
            onSaveMarca = { true },
            onDeleteMarca = {},
            irAMarcaList = {},
            onNombreMarcaChanged = {},
        ) {

        }
    }
}