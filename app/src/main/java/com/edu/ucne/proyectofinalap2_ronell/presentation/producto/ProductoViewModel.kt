package com.edu.ucne.proyectofinalap2_ronell.presentation.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.ProductoEntity
import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.ProductoDto
import com.edu.ucne.proyectofinalap2_ronell.data.repository.CategoriaRepository
import com.edu.ucne.proyectofinalap2_ronell.data.repository.MarcaRepository
import com.edu.ucne.proyectofinalap2_ronell.data.repository.ProductoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoRepository: ProductoRepositoryImpl,
    private val categoriaRepository: CategoriaRepository,
    private val marcaRepository: MarcaRepository

) : ViewModel() {

    private val _showBarcodeScanner = MutableStateFlow(false)
    val showBarcodeScanner: StateFlow<Boolean> = _showBarcodeScanner.asStateFlow()

    fun onBarcodeScannerToggle() {
        _showBarcodeScanner.value = !_showBarcodeScanner.value
    }

    fun onBarcodeScanned(barcode: String) {
        uiState.update { currentState ->
            currentState.copy(codigoBarras = barcode)
        }
    }


    val productoId: Int = 0

    var uiState = MutableStateFlow(ProductoUiState())
        private set


    val categoria = categoriaRepository.getCategoriaLocal()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val marca = marcaRepository.getMarcasLocal()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    init {
        getProductosLocal()
        getCategoriasLocal()
        getMarcasLocal()


        viewModelScope.launch {
            val producto = productoRepository.getProductoLocal(productoId)

            producto?.let {
                uiState.update {
                    it.copy(
                        productoId = producto.productoId,
                        nombre = producto.nombreProducto,
                        descripcion = producto.descripcionProducto,
                        fechaVencProducto = producto.fechaVencProducto,
                        stock = producto.stockProducto,
                        categoriaId = producto.categoriaId,
                        marcaId = producto.marcaId,
                        codigoBarras = producto.codigoBarrasProducto,
                        precioCompra = producto.precioCompraProducto,
                        precioVenta = producto.precioVentaProducto
                    )
                }
            }
        }
    }


    fun getCategoriasLocal() {
        viewModelScope.launch {
            categoriaRepository.getCategoriaLocal().collect {
                categorias ->
                    uiState.update { it.copy(categorias = categorias) }

            }
        }
    }

    fun getMarcasLocal() {
        viewModelScope.launch {
            marcaRepository.getMarcasLocal().collect{
                marcas ->
            uiState.update { it.copy(marcas = marcas) }

            }
        }
    }
    fun onNombreChanged(nombre: String) {
        uiState.update {
            it.copy(nombre = nombre)
        }
    }

    fun onDescripcionChanged(descripcion: String) {
        uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onFechaVencProductoChanged(fechaVenc: String) {
        uiState.update {
            it.copy(fechaVencProducto = fechaVenc)
        }
    }

    fun onStockChanged(stock: Int) {
        uiState.update {
            it.copy(stock = stock)
        }
    }

    fun onCategoriaChanged(categoriaId: Int){
        uiState.update {
            it.copy(categoriaId = categoriaId)
        }
    }

    fun onMarcaChanged(marcaId: Int){
        uiState.update {
            it.copy(marcaId = marcaId)
        }
    }

    fun onCodigoBarrasChanged(codigoBarras: String) {
        uiState.update {
            it.copy(codigoBarras = codigoBarras)
        }
    }

    fun onPrecioCompraChanged(precioCompra: Double) {
        uiState.update {
            it.copy(precioCompra = precioCompra)
        }
    }

    fun onPrecioVentaChanged(precioVenta: Double) {
        uiState.update {
            it.copy(precioVenta = precioVenta)
        }
    }

    fun onSetProducto(productoId: Int) {
        viewModelScope.launch {
            val producto = productoRepository.getProductoLocal(productoId)
            producto?.let {
                uiState.update {
                    it.copy(
                        productoId = producto.productoId,
                        nombre = producto.nombreProducto,
                        descripcion = producto.descripcionProducto,
                        fechaVencProducto = producto.fechaVencProducto,
                        stock = producto.stockProducto,
                        categoriaId = producto.categoriaId,
                        marcaId = producto.marcaId,
                        codigoBarras = producto.codigoBarrasProducto,
                        precioCompra = producto.precioCompraProducto,
                        precioVenta = producto.precioVentaProducto
                    )
                }
            }
        }
    }

    fun getProductosLocal() {
        viewModelScope.launch {
            productoRepository.getProductosLocal()
                .collect { productos ->
                uiState.update { it.copy(productos = productos) }


            }
        }
    }


    fun postProducto(): Boolean {
        viewModelScope.launch {
            productoRepository.saveProducto(uiState.value.toEntitu())
            uiState.value = ProductoUiState()
        }
        return true
    }

    fun newProducto() {
        viewModelScope.launch {
            uiState.value = ProductoUiState()
        }
    }

    fun deleteProducto() {
        viewModelScope.launch {
            productoRepository.deleteProductoLocal(uiState.value.toEntitu())
            getProductosLocal()
            uiState.update {
                ProductoUiState()
            }
        }
    }


}


data class ProductoUiState(
    val productoId: Int? = null,
    var nombre: String = "",
    var nombreError: String? = null,
    var descripcion: String = "",
    var descripcionError: String? = null,
    var fechaVencProducto: String = "",
    var fechaVencProductoError: String? = null,
    var stock: Int? = 0,
    var stockError: String? = null,
    val isLoading: Boolean = false,
    val errorMessagge: String? = null,
    val productos: List<ProductoEntity> = emptyList(),
    val categorias: List<CategoriaEntity> = emptyList(),
    val marcas: List<MarcaEntity> = emptyList(),
    var categoriaId: Int? = null,
    var marcaId: Int? = null,
    var codigoBarras: String = "",
    var codigoBarrasError: String? = null,
    var precioCompra: Double? = 0.0,
    var precioCompraError: String? = null,
    var precioVenta: Double? = 0.0,
    var precioVentaError: String? = null,
    )

fun ProductoUiState.toDTO() = ProductoDto(
    productoId = productoId ?: 0,
    nombreProducto = nombre,
    descripcionProducto = descripcion,
    fechaVencProducto = fechaVencProducto,
    stockProducto = stock ?: 0,
    categoriaId = categoriaId ?: 0,
    marcaId = marcaId ?: 0,
    precioCompraProducto = precioCompra ?:0.0,
    precioVentaProducto = precioVenta ?: 0.0,
    codigoBarrasProducto = codigoBarras
)


fun ProductoUiState.toEntitu(): ProductoEntity {
    return ProductoEntity(
        productoId = productoId ?: 0,
        nombreProducto = nombre,
        descripcionProducto = descripcion,
        fechaVencProducto = fechaVencProducto,
        stockProducto = stock ?: 0,
        categoriaId = categoriaId ?: 0,
        marcaId = marcaId ?: 0,
        precioCompraProducto = precioCompra ?:0.0,
        precioVentaProducto = precioVenta ?: 0.0,
        codigoBarrasProducto = codigoBarras
    )
}