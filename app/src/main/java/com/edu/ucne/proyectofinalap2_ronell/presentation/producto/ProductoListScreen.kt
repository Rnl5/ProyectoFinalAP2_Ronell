package com.edu.ucne.proyectofinalap2_ronell.presentation.producto

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
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.ProductoEntity
import com.edu.ucne.proyectofinalap2_ronell.presentation.components.BotonFlotante

@Composable
fun ProductoListScreen(
    viewModel: ProductoViewModel = hiltViewModel(),
    onVerProducto: (ProductoEntity) -> Unit,
    onAddProducto: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductoListBody(
        productos = uiState.productos,
        onVerProducto = onVerProducto,
        onAddProducto = onAddProducto,
        onGetProductos = { viewModel.getProductosLocal() },
        uistate = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoListBody(
    productos: List<ProductoEntity>,
    onVerProducto: (ProductoEntity) -> Unit,
    onAddProducto: () -> Unit,
    onGetProductos: () -> Unit,
    uistate: ProductoUiState,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "Lista de Productos")
                        TextButton(onClick = { onGetProductos() }) {
                            Text(text = "Get Productos")
                        }
                        if (uistate.isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            )
        }, floatingActionButton = {
            BotonFlotante(
                title = "Agregar producto",
                onAddProducto
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

                Text(
                    text = "Fecha",
                    modifier = Modifier.weight(0.40f)
                )

                Text(
                    text = "Stock",
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
                items(productos) { item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onVerProducto(item)
                        }
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = item.productoId.toString(),
                            modifier = Modifier.weight(0.20f)
                        )
                        Text(
                            text = item.nombreProducto,
                            modifier = Modifier.weight(0.40f)
                        )
                        Text(
                            text = item.descripcionProducto,
                            modifier = Modifier.weight(0.70f)
                        )
                        Text(
                            text = item.fechaVencProducto,
                            modifier = Modifier.weight(0.70f)
                        )
                        Text(
                            text = item.stockProducto.toString(),
                            modifier = Modifier.weight(0.40f)
                        )
                    }
                }
            }
        }
    }
}