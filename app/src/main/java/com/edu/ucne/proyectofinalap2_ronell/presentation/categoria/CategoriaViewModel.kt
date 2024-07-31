package com.edu.ucne.proyectofinalap2_ronell.presentation.categoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.CategoriaDto
import com.edu.ucne.proyectofinalap2_ronell.data.repository.CategoriaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val categoriaRepository: CategoriaRepository,
) : ViewModel() {
    val categoriaId: Int = 0

    var uiState = MutableStateFlow(CategoriaUiState())
        private set

    init {

        viewModelScope.launch {
            val categoria = categoriaRepository.getCategoriaLocal(categoriaId)

            categoria?.let {
                uiState.update {
                    it.copy(
                        categoriaId = categoria.categoriaId,
                        nombreCategoria = categoria.nombreCategoria,
                        descripcionCategoria = categoria.descripcionCategoria,
                    )
                }
            }
        }
    }

    fun onNombreCategoriaChanged(nombreCategoria: String) {
        uiState.update {
            it.copy(nombreCategoria = nombreCategoria)
        }
    }

    fun onDescripcionCategoriaChanged(descripcionCategoria: String) {
        uiState.update {
            it.copy(descripcionCategoria = descripcionCategoria)
        }
    }

    fun onSetCategoria(categoriaId: Int) {
        viewModelScope.launch {
            val categoria = categoriaRepository.getCategoriaLocal(categoriaId)
            categoria?.let {
                uiState.update {
                    it.copy(
                        categoriaId = categoria.categoriaId,
                        nombreCategoria = categoria.nombreCategoria,
                        descripcionCategoria = categoria.descripcionCategoria,
                    )
                }
            }
        }
    }

    fun getCategoriasLocal() {
        viewModelScope.launch {
            categoriaRepository.getCategoriaLocal().collect { categorias ->
                uiState.update { it.copy(categorias = categorias) }
            }
        }
    }

    fun postCategoria(): Boolean {
        viewModelScope.launch {
            categoriaRepository.saveCategoria(uiState.value.toEntitu())
            uiState.value = CategoriaUiState()
        }
        return true
    }

    fun newCategoria() {
        viewModelScope.launch {
            uiState.value = CategoriaUiState()
        }
    }

    fun deleteCategoria() {
        viewModelScope.launch {
            categoriaRepository.deleteCategoriaLocal(uiState.value.toEntitu())
            uiState.update {
                CategoriaUiState()
            }
        }
    }
}


    data class CategoriaUiState(
        val categoriaId: Int? = null,
        var nombreCategoria: String = "",
        var nombreCategoriaError: String? = null,
        var descripcionCategoria: String = "",
        var descripcionCategoriaError: String? = null,
        val categorias: List<CategoriaEntity> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessagge: String? = null,
    )

    fun CategoriaUiState.toDTO() = CategoriaDto(
        categoriaId = categoriaId ?: 0,
        nombreCategoria = nombreCategoria,
        descripcionCategoria = descripcionCategoria,
    )

    fun CategoriaUiState.toEntitu(): CategoriaEntity {
        return CategoriaEntity(
            categoriaId = categoriaId ?: 0,
            nombreCategoria = nombreCategoria,
            descripcionCategoria = descripcionCategoria,
        )
    }
