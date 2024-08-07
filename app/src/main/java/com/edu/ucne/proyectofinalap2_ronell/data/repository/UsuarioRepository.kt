package com.edu.ucne.proyectofinalap2_ronell.data.repository

import com.edu.ucne.proyectofinalap2_ronell.data.local.dao.UsuarioDao
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsuarioRepository  @Inject constructor(
    private val usuarioDao: UsuarioDao
){
    suspend fun saveUsuario(usuario: UsuarioEntity) =
        usuarioDao.saveUsuario(usuario)

    fun getUsuarioLocal(): Flow<List<UsuarioEntity>> =
        usuarioDao.getAllUsuarios()

    suspend fun deleteUsuarioLocal(usuario: UsuarioEntity) =
        usuarioDao.deleteUsuario(usuario)

    suspend fun getUsuarioLocal(usuarioId: Int): UsuarioEntity? =
        usuarioDao.findUsuario(usuarioId)

    suspend fun getUserByEmail(correo: String) =
        usuarioDao.getUsuarioByEmail(correo)
}