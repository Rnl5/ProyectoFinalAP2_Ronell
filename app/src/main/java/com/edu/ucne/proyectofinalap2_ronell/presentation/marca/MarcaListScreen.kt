package com.edu.ucne.proyectofinalap2_ronell.presentation.marca

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import com.edu.ucne.proyectofinalap2_ronell.presentation.components.BotonFlotante

@Composable
fun MarcaListScreen(
    viewModel: MarcaViewModel = hiltViewModel(),
    onVerMarca: (MarcaEntity) -> Unit,
    onAddMarca: () -> Unit,
    irADashboard: () -> Unit,

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MarcaListBody(
        marcas = uiState.marcas,
        onVerMarca = onVerMarca,
        onAddMarca = onAddMarca,
        onGetMarcas = { viewModel.getMarcasLocal() },
        uistate = uiState,
        irAProductoList = { irADashboard() },

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarcaListBody(
    marcas: List<MarcaEntity>,
    onVerMarca: (MarcaEntity) -> Unit,
    onAddMarca: () -> Unit,
    onGetMarcas: () -> Unit,
    uistate: MarcaUiState,
    irAProductoList: () -> Unit,

    ) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "Lista de Marcas")
                        TextButton(onClick = { onGetMarcas() }) {
                            Text(text = "Get Marcas")
                        }
                        if (uistate.isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = irAProductoList) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atras"
                        )
                    }
                },
            )
        }, floatingActionButton = {
            BotonFlotante(
                title = "Agregar marca",
                onAddMarca
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Id",
                    modifier = Modifier.weight(0.20f)
                )

                Text(
                    text = "Nombre",
                    modifier = Modifier.weight(0.40f)
                )
            }

            if (uistate.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(marcas) { item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onVerMarca(item)
                        }
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = item.marcaId.toString(),
                            modifier = Modifier.weight(0.20f)
                        )
                        Text(
                            text = item.nombreMarca,
                            modifier = Modifier.weight(0.40f)
                        )
                    }
                }
            }
        }
    }
}