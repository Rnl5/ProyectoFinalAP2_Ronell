package com.edu.ucne.proyectofinalap2_ronell.presentation.Inventario

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.ProductoEntity
import com.edu.ucne.proyectofinalap2_ronell.presentation.producto.ProductoUiState
import com.edu.ucne.proyectofinalap2_ronell.presentation.producto.ProductoViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun InventarioListScreenStockBajo(
    viewModel: ProductoViewModel = hiltViewModel(),
    onVerProducto: (ProductoEntity) -> Unit,
    onAddProducto: () -> Unit,
    irADashboard: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    InventarioListStockBajoBody(
        productos = uiState.productos,
        onVerProducto = onVerProducto,
        onAddProducto = onAddProducto,
        onGetProductos = { viewModel.getProductosLocal() },
        uistate = uiState,
        categorias = uiState.categorias,
        marcas = uiState.marcas,
        irAProductoList = { irADashboard() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioListStockBajoBody(
    productos: List<ProductoEntity>,
    onVerProducto: (ProductoEntity) -> Unit,
    onAddProducto: () -> Unit,
    onGetProductos: () -> Unit,
    uistate: ProductoUiState,
    categorias: List<CategoriaEntity>,
    marcas: List<MarcaEntity>,
    irAProductoList: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategorias by remember { mutableStateOf<Set<CategoriaEntity?>>(emptySet()) }
    var selectedMarcas by remember { mutableStateOf<Set<MarcaEntity?>>(emptySet()) }

    val filteredProductos by remember(
        searchQuery,
        selectedCategorias,
        selectedMarcas,
        productos,
        categorias,
        marcas
    ) {
        derivedStateOf {
            productos.filter { producto ->
                val matchesSearch = producto.nombreProducto.contains(searchQuery, ignoreCase = true)
                val matchesCategoria =
                    selectedCategorias.isEmpty() || selectedCategorias.any { it?.categoriaId == producto.categoriaId }
                val matchesMarca =
                    selectedMarcas.isEmpty() || selectedMarcas.any { it?.marcaId == producto.marcaId }
                val bajoStock = producto.stockProducto <= 5

                matchesSearch && matchesCategoria && matchesMarca && bajoStock
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Productos con Stock Bajo") },
                actions = {
                    IconButton(onClick = onGetProductos) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = irAProductoList) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atras"
                        )
                    }
                },)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProducto) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            BarraBusqueda(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })

            if (uistate.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRow(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategorias.isEmpty(),
                            onClick = { selectedCategorias = emptySet() },
                            label = { Text("Todas") }
                        )
                    }
                    items(categorias) { categoria ->
                        Spacer(modifier = Modifier.width(4.dp))
                        FilterChip(
                            selected = categoria in selectedCategorias,
                            onClick = {
                                selectedCategorias = if (categoria in selectedCategorias) {
                                    selectedCategorias - categoria
                                } else {
                                    selectedCategorias + categoria
                                }
                            },
                            label = { Text(categoria.nombreCategoria) }
                        )
                    }
                }
                Divider()
                // Chips para Marcas
                LazyRow(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedMarcas.isEmpty(),
                            onClick = { selectedMarcas = emptySet() },
                            label = { Text("Todas") }
                        )
                    }
                    items(marcas) { marca ->
                        Spacer(modifier = Modifier.width(4.dp))
                        FilterChip(
                            selected = marca in selectedMarcas,
                            onClick = {
                                selectedMarcas = if (marca in selectedMarcas) {
                                    selectedMarcas - marca
                                } else {
                                    selectedMarcas + marca
                                }
                            },
                            label = { Text(marca.nombreMarca) }
                        )
                    }
                }
            }

            Divider()


//            if (uistate.isLoading) {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            } else {
            LazyColumn {
                items(filteredProductos) { producto ->
                    val categoria =
                        categorias.find { it.categoriaId == producto.categoriaId }
                    val marca = marcas.find { it.marcaId == producto.marcaId }
                    ProductoItemCompacto(
                        producto,
                        onVerProducto,
                        categoria,
                        marca
                    )
                    Divider()
                }
            }
//            }
        }
    }
}

@Composable
fun ProductoItemCompacto(
    producto: ProductoEntity,
    onVerProducto: (ProductoEntity) -> Unit,
    categoria: CategoriaEntity?,
    marca: MarcaEntity?,
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//    val fechaVencimiento = LocalDate.parse(producto.fechaVencProducto, formatter)
    val fechaVencimiento = if (producto.fechaVencProducto.isNotEmpty()) {
        LocalDate.parse(producto.fechaVencProducto, formatter)
    } else {
        // Manejar el caso de una cadena vacía, por ejemplo, asignar una fecha por defecto o null
        LocalDate.MAX
    }
    val hoy = LocalDate.now()
    val diasHastaVencimiento = ChronoUnit.DAYS.between(hoy, fechaVencimiento ?: LocalDate.MAX)

    val (iconoEstado, colorEstado) = when {
        producto.stockProducto <= 10 -> Pair(Icons.Default.Warning, Color.Yellow)
        diasHastaVencimiento in 0..5 -> Pair(Icons.Default.Warning, Color.Yellow)
        else -> Pair(Icons.Default.CheckCircle, Color.Green)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerProducto(producto) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = producto.nombreProducto,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = producto.descripcionProducto,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Categoría: ${categoria?.nombreCategoria ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Marca: ${marca?.nombreMarca ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Stock: ${producto.stockProducto}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Vence: ${producto.fechaVencProducto}",
                style = MaterialTheme.typography.bodySmall
            )
            Icon(
                imageVector = iconoEstado,
                contentDescription = null,
                tint = colorEstado
            )
        }
    }
}


@Composable
fun BarraBusqueda(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text("Buscar producto...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

    // ... (mantén el resto del código igual)
