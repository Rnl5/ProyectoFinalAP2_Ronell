package com.edu.ucne.proyectofinalap2_ronell.presentation.categoria

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.presentation.components.BotonFlotante

@Composable
fun CategoriaListScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    onVerCategoria: (CategoriaEntity) -> Unit,
    onAddCategoria: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CategoriaListBody(
        categorias = uiState.categorias,
        onVerCategoria = onVerCategoria,
        onAddCategoria = onAddCategoria,
        onGetCategorias = { viewModel.getCategoriasLocal() },
        uistate = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaListBody(
    categorias: List<CategoriaEntity>,
    onVerCategoria: (CategoriaEntity) -> Unit,
    onAddCategoria: () -> Unit,
    onGetCategorias: () -> Unit,
    uistate: CategoriaUiState,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "Lista de Categorias")
                        TextButton(onClick = { onGetCategorias() }) {
                            Text(text = "Get Categorias")
                        }
                        if (uistate.isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            )
        }, floatingActionButton = {
            BotonFlotante(
                title = "Agregar Categoria",
                onAddCategoria
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

                Text(
                    text = "Descripcion",
                    modifier = Modifier.weight(0.70f)
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
                items(categorias) { item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onVerCategoria(item)
                        }
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = item.categoriaId.toString(),
                            modifier = Modifier.weight(0.20f)
                        )
                        Text(
                            text = item.nombreCategoria,
                            modifier = Modifier.weight(0.40f)
                        )
                        Text(
                            text = item.descripcionCategoria,
                            modifier = Modifier.weight(0.70f)
                        )
                    }
                }
            }
        }
    }
}