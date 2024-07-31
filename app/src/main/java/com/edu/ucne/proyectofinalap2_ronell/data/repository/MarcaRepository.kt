package com.edu.ucne.proyectofinalap2_ronell.data.repository

import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.MarcaDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.MarcaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarcaRepository @Inject constructor(
    private val marcaDao: MarcaDao,
) {
    suspend fun saveMarca(marca: MarcaEntity) =
        marcaDao.saveMarca(marca)

    fun getMarcasLocal(): Flow<List<MarcaEntity>> =
        marcaDao.getAllMarcas()

    suspend fun deleteMarcaLocal(marca: MarcaEntity) =
        marcaDao.deleteMarca(marca)

    suspend fun getMarcaLocal(marcaId: Int): MarcaEntity? =
        marcaDao.findMarca(marcaId)
}