package com.edu.ucne.proyectofinalap2_ronell.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.CategoriaEntity
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Upsert
    suspend fun saveUsuario(usuario: UsuarioEntity)

    @Query(
        """
            SELECT *
            FROM Usuarios
            WHERE usuarioId=:id
            LIMIT 1
        """
    )
    suspend fun findUsuario(id: Int) : UsuarioEntity?

    @Delete
    suspend fun deleteUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM Usuarios")
    fun getAllUsuarios(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM Usuarios WHERE correoElectronico = :correoElectronico LIMIT 1")
    suspend fun getUsuarioByEmail(correoElectronico: String): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsuarios(usuarios: List<UsuarioEntity>)


}