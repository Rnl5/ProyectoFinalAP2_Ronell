package com.edu.ucne.proyectofinalap2_ronell.presentation.marca

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.remote.dto.MarcaDto
import com.edu.ucne.proyectofinalap2_ronell.data.repository.MarcaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarcaViewModel @Inject constructor(
    private val marcaRepository: MarcaRepository,
) : ViewModel() {
    val marcaId: Int = 0

    var uiState = MutableStateFlow(MarcaUiState())
        private set

    init {
        getMarcasLocal()

        viewModelScope.launch {
            val marca = marcaRepository.getMarcaLocal(marcaId)

            marca?.let {
                uiState.update {
                    it.copy(
                        marcaId = marca.marcaId,
                        nombreMarca = marca.nombreMarca
                    )
                }
            }
        }
    }

    fun onNombreMarcaChanged(nombreMarca: String) {
        uiState.update {
            it.copy(nombreMarca = nombreMarca)
        }
    }

    fun onSetMarca(marcaId: Int) {
        viewModelScope.launch {
            val marca = marcaRepository.getMarcaLocal(marcaId)
            marca?.let {
                uiState.update {
                    it.copy(
                        marcaId = marca.marcaId,
                        nombreMarca = marca.nombreMarca
                    )
                }
            }
        }
    }

    fun getMarcasLocal() {
        viewModelScope.launch {
            marcaRepository.getMarcasLocal().collect { marcas ->
                uiState.update { it.copy(marcas = marcas) }

            }
        }
    }


    fun postMarca(): Boolean {
        viewModelScope.launch {
            marcaRepository.saveMarca(uiState.value.toEntitu())
            uiState.value = MarcaUiState()
        }
        return true
    }

    fun newMarca() {
        viewModelScope.launch {
            uiState.value = MarcaUiState()
        }
    }

    fun deleteMarca() {
        viewModelScope.launch {
            marcaRepository.deleteMarcaLocal(uiState.value.toEntitu())
            getMarcasLocal()
            uiState.update {
                MarcaUiState()
            }
        }
    }


}


data class MarcaUiState(
    val marcaId: Int? = null,
    var nombreMarca: String = "",
    var nombreMarcaError: String? = null,
    val isLoading: Boolean = false,
    val errorMessagge: String? = null,
    val marcas: List<MarcaEntity> = emptyList(),
)

fun MarcaUiState.toDTO() = MarcaDto(
    marcaId = marcaId ?: 0,
    nombreMarca = nombreMarca,
)


fun MarcaUiState.toEntitu(): MarcaEntity {
    return MarcaEntity(
        marcaId = marcaId ?: 0,
        nombreMarca = nombreMarca,
    )
}