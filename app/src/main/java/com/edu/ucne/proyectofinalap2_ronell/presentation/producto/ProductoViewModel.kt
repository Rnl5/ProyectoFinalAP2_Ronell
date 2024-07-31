package com.edu.ucne.proyectofinalap2_ronell.presentation.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.ProductoEntity
import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.ProductoDto
import com.edu.ucne.proyectofinalap2_ronell.data.repository.ProductoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val productoRepository: ProductoRepositoryImpl,
) : ViewModel() {
    val productoId: Int = 0

    var uiState = MutableStateFlow(ProductoUiState())
        private set

    init {
//        getProductos()
        getProductosLocal()

        viewModelScope.launch {
            val producto = productoRepository.getProductoLocal(productoId)

            producto?.let {
                uiState.update {
                    it.copy(
                        productoId = producto.productoId,
                        nombre = producto.nombreProducto,
                        descripcion = producto.descripcionProducto,
                        fechaVencProducto = producto.fechaVencProducto,
                        precio = producto.stockProducto
                    )
                }
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

    fun onPrecioChanged(precio: Int) {
        uiState.update {
            it.copy(precio = precio)
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
                        precio = producto.stockProducto
                    )
                }
            }
        }
    }

    fun getProductosLocal() {
        viewModelScope.launch {
            productoRepository.getProductosLocal().collect { productos ->
                uiState.update { it.copy(productos = productos) }

            }
        }
    }

//    fun getProductos() {
//        viewModelScope.launch {
//            productoRepository.getProductosLocal().onEach { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        uiState.update {
//                            it.copy(
//                                isLoading = true
//                            )
//                        }
//                        delay(1500)
//                    }
//
//                    is Resource.Success -> {
//                        uiState.update {
//                            it.copy(
//                                isLoading = false,
//                                productos = result.data ?: emptyList()
//                            )
//                        }
//                    }
//
//                    is Resource.Error -> {
//                        uiState.update {
//                            it.copy(
//                                isLoading = false,
//                                errorMessagge = result.message
//                            )
//                        }
//                    }
//                }
//            }.launchIn(viewModelScope)
//        }
//    }

    fun postProducto(): Boolean {
        viewModelScope.launch {
            productoRepository.saveProducto(uiState.value.toEntitu())
            uiState.value = ProductoUiState()

//                if(uiState.value.productoId == null || uiState.value.productoId == 0) {
//                    productoRepository.postProducto(uiState.value.toDTO())
//                    uiState.value = ProductoUiState()
//                } else {
//                    productoRepository.putProducto(uiState.value.toDTO())
//                    uiState.value = ProductoUiState()
//            }
//            uiState.update { ProductoUiState() }
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
//            getProductos()
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
    var precio: Int? = 0,
    var precioError: String? = null,
    val isLoading: Boolean = false,
    val errorMessagge: String? = null,
//    val productos: List<ProductoDto> = emptyList(),
    val productos: List<ProductoEntity> = emptyList(),

    )

fun ProductoUiState.toDTO() = ProductoDto(
    productoId = productoId ?: 0,
    nombreProducto = nombre,
    descripcionProducto = descripcion,
    fechaVencProducto = fechaVencProducto,
    stockProducto = precio ?: 0,
)


fun ProductoUiState.toEntitu(): ProductoEntity {
    return ProductoEntity(
        productoId = productoId ?: 0,
        nombreProducto = nombre,
        descripcionProducto = descripcion,
        fechaVencProducto = fechaVencProducto,
        stockProducto = precio ?: 0,
    )
}