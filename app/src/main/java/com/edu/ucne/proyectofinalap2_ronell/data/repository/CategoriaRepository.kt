package com.edu.ucne.proyectofinalap2_ronell.data.repository

import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.CategoriaDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val categoriaDao: CategoriaDao,
) {
    suspend fun saveCategoria(categoria: CategoriaEntity) =
        categoriaDao.saveCategoria(categoria)

    fun getCategoriaLocal(): Flow<List<CategoriaEntity>> =
        categoriaDao.getAllCategorias()

    suspend fun deleteCategoriaLocal(categoria: CategoriaEntity) =
        categoriaDao.deleteCategoria(categoria)

    suspend fun getCategoriaLocal(categoriaId: Int): CategoriaEntity? =
        categoriaDao.findCategoria(categoriaId)

}